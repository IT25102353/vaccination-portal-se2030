# OpenVaccine

A web portal for managing a vaccination programme: vaccine stock, appointments, dose records, centers, adverse reactions and reports. Built as a first-year SE2030 project to practise object-oriented design.

[Tech Stack](#tech-stack) · [Features](#features) · [Design Patterns](#design-patterns) · [Run It](#run-it)

## Tech Stack

| Part | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot |
| Pages | Thymeleaf templates, Bootstrap 5 |
| Database | MySQL (runs in Docker) |
| Build tool | Maven |
| Tests | JUnit 5, Mockito |

The code is split into layers: **model** (the data), **repository** (database access), **service** (the rules) and **controller** (web pages).

## Features

- **Inventory:** track vaccine batches, quantities and expiry dates. A Status column shows `VALID`, `EXPIRING SOON` or `EXPIRED` automatically.
- **Appointments:** book and manage patient appointments.
- **Dose Records:** record administered doses and certificate numbers.
- **Centers:** manage vaccination centers and their daily capacity.
- **Adverse Reactions:** log and follow up on reactions after vaccination.
- **Reports:** create reports. Leave the summary empty and it is written for you from real data.
- **Dashboard:** live totals and a list of stock that is expired or expiring soon.
- **Input checks:**
  - Names, batch numbers and locations allow only letters and numbers.
  - The received date must be before the expiry date.
  - Contact numbers must be exactly 10 digits.
- **Interface:** search on every list, success messages that disappear after a few seconds, and a light/dark theme switch.

## Design Patterns

All pattern code is in `src/main/java/com/se2030/vaccination_portal/pattern/`.

### 1. Strategy (`pattern/strategy`)

**Problem it solves:** the same validation `if` statements and regexes were copied into several services. A change meant editing many places.

**Before:**
```java
if (!stock.getBatchNumber().matches("[A-Za-z0-9 ]+")) { throw ... }
if (!stock.getManufacturer().matches("[A-Za-z0-9 ]+")) { throw ... }
if (!stock.getStorageLocation().matches("[A-Za-z0-9 ]+")) { throw ... }
```

**After:** each rule is one small class, and the service just uses it.
```java
private final ValidationStrategy<String> batchNumberRule = new AlphanumericRule("Batch number");

batchNumberRule.validate(stock.getBatchNumber());   // throws "Batch number can only contain letters and numbers"
```

**Result:** a rule is written once and reused. A new rule is a new class, and no service needs to change. The expiry status works the same way: `WarningWindowExpiryStatus(30)` can be swapped for `WarningWindowExpiryStatus(60)` without touching `VaccineStock`.

### 2. Observer (`pattern/observer`)

**Problem it solves:** recording a dose should also reduce stock and write an audit log. Putting all that inside `DoseRecordService` would mix the Dose Records and Inventory modules together.

**How it works:** the service only announces "a dose was recorded". Observers listen and each does its own job.
```java
DoseRecord saved = doseRecordRepository.save(doseRecord);
for (Observer<DoseRecord> observer : observers) {
    observer.update(saved);
}
```

**Showcase:** record one Covishield dose.
| Step | Who does it |
|---|---|
| Dose is saved | `DoseRecordService` |
| Covishield stock goes from 500 to 499 | `StockDeductionObserver` |
| `Dose recorded: Covishield (dose 1) for Kasun Perera` appears in the log | `DoseAuditObserver` |

**Result:** to add another reaction (for example an email), write a new observer class. `DoseRecordService` stays the same. Reporting a `SEVERE` adverse reaction works the same way through `SevereReactionAlertObserver`.

### 3. Factory Method (`pattern/factory`)

**Problem it solves:** report summaries were typed by hand, and `ReportService` would need a long `if / else` to build a different summary for each report type.

**How it works:** `ReportService` asks the factory for a generator and does not care which one it gets.
```java
ReportGenerator generator = reportGeneratorFactory.createGenerator(report.getReportType());
report.setSummary(generator.generateSummary(report.getPeriodStart(), report.getPeriodEnd()));
```

**Showcase:** create a report with the Summary box left empty.
| Report type | Generated summary |
|---|---|
| `INVENTORY` | Inventory: 2 batches, 800 doses in stock. 0 expired, 1 expiring soon. |
| `APPOINTMENTS` | Appointments from 2026-08-10 to 2026-08-16: 2 in total, 1 completed, 0 cancelled. |
| `ADVERSE_REACTIONS` | Adverse reactions from 2026-08-10 to 2026-08-16: 1 reported, 0 severe. |
| `GENERAL` | General: 2 doses administered from 2026-08-10 to 2026-08-16 across 2 registered centers. |

**Result:** a new report type is a new generator class plus one line in the factory. `ReportService` stays the same.

## Run It

```
docker compose up -d
./mvnw spring-boot:run
```

Open http://localhost:8080 and sign in with `admin` / `admin123`.

Run the tests with `./mvnw test`. A step-by-step walkthrough of every module is in [TRY_OUT.md](TRY_OUT.md).
