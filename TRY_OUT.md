# Try Out Guide, OpenVaccine

This guide walks through testing every function of the OpenVaccine portal in the correct order. Some modules depend on data created in an earlier module (for example, Appointments needs a vaccine and a center to already exist), so please follow the order below.

Before starting, make sure the app is running.

```
docker compose up -d
./mvnw spring-boot:run
```

Then open `http://localhost:8080` in your browser.

## 1. Sign In

1. Open `http://localhost:8080`. You will land on the OpenVaccine welcome page.
2. Click **Access Portal**. Since you are not signed in yet, you will be redirected to the login page.
3. Enter the demo credentials.
   - Username: `admin`
   - Password: `admin123`
4. Click **Sign in**. You should now land on the Dashboard at `/dashboard`.
5. To confirm logout works, click **Logout** in the top navigation. You should be sent back to the landing page. Sign in again before continuing to the next steps.

## 2. Vaccine Stock and Inventory

Go to **Inventory** in the navigation. This module is the source of vaccine names used later in Appointments, Dose Records and Adverse Reactions, so create records here first.

### Create

Click **Add Stock** and fill in the form.

| Field | Value |
|---|---|
| Vaccine Name | Covishield |
| Batch Number | CV-2026-014 |
| Manufacturer | Serum Institute |
| Quantity Available | 500 |
| Received Date | 2026-07-01 |
| Expiry Date | 2027-06-30 |
| Storage Location | Cold Room A |

Save it, then repeat with a second entry so the dropdowns in later modules have more than one option.

| Field | Value |
|---|---|
| Vaccine Name | Pfizer BioNTech |
| Batch Number | PB-2026-088 |
| Manufacturer | Pfizer |
| Quantity Available | 300 |
| Received Date | 2026-07-10 |
| Expiry Date | 2027-01-10 |
| Storage Location | Cold Room B |

### View

Confirm both entries appear in the Inventory table.

### Update

Click **Edit** on the Covishield row, change Quantity Available to `480`, and save. Confirm the table shows the updated quantity.

### Delete

Click **Delete** on any test entry you want to remove and confirm in the popup. Keep at least Covishield and Pfizer BioNTech for the next steps.

## 3. Vaccination Center and Facility Management

Go to **Centers**. This module is the source of center names used later in Appointments, Dose Records and Adverse Reactions.

### Create

Click **Add Center** and fill in the form.

| Field | Value |
|---|---|
| Center Name | Kurunegala Base Hospital |
| Address | Hospital Road, Kurunegala |
| District | Kurunegala |
| Contact Number | 037-2222222 |
| Capacity Per Day | 150 |
| Operating Hours | 8.00 AM to 4.00 PM |
| Status | ACTIVE |

Save it, then repeat with a second entry.

| Field | Value |
|---|---|
| Center Name | Kandy General Hospital |
| Address | William Gopallawa Mawatha, Kandy |
| District | Kandy |
| Contact Number | 081-2233445 |
| Capacity Per Day | 200 |
| Operating Hours | 8.00 AM to 5.00 PM |
| Status | ACTIVE |

### View

Confirm both centers appear in the Centers table with a green ACTIVE badge.

### Update

Edit Kurunegala Base Hospital, change Status to `INACTIVE`, save, and confirm the badge changes color. Change it back to `ACTIVE` afterward so it is usable in later steps.

### Delete

Try deleting a test center you do not need. Keep at least Kurunegala Base Hospital and Kandy General Hospital for the next steps.

## 4. Vaccine Appointment and Scheduling

Go to **Appointments**. This module is also the source of patients used later in Dose Records and Adverse Reactions, so create at least two here.

### Create

Click **New Appointment** and fill in the form. Vaccine Name and Vaccination Center are dropdowns populated from the Inventory and Centers modules.

| Field | Value |
|---|---|
| Patient Name | Kasun Perera |
| Patient NIC | 199512345678 |
| Contact Number | 071-1234567 |
| Vaccine Name | Covishield |
| Vaccination Center | Kurunegala Base Hospital |
| Status | SCHEDULED |
| Appointment Date | 2026-08-15 |
| Appointment Time | 09:30 |

Save it, then repeat with a second patient.

| Field | Value |
|---|---|
| Patient Name | Nadeesha Wickramasinghe |
| Patient NIC | 200013456789 |
| Contact Number | 077-9876543 |
| Vaccine Name | Pfizer BioNTech |
| Vaccination Center | Kandy General Hospital |
| Status | SCHEDULED |
| Appointment Date | 2026-08-16 |
| Appointment Time | 10:00 |

### View

Confirm both appointments appear with a status badge.

### Update

Edit Kasun Perera's appointment, change Status to `COMPLETED`, and save. Confirm the badge turns green.

### Delete

Try deleting a test appointment you do not need. Keep Kasun Perera and Nadeesha Wickramasinghe for the next steps, since Dose Records and Adverse Reactions rely on them being registered patients.

## 5. Dose Administration Logs and Certificate System

Go to **Dose Records**. Patient NIC, Vaccine Name and Center Name are all dropdowns here. Selecting a Patient NIC automatically fills in the Patient Name field, so you do not retype it.

### Create

Click **New Dose Record** and fill in the form.

| Field | Value |
|---|---|
| Patient NIC | Select Kasun Perera (199512345678) |
| Patient Name | Filled automatically |
| Vaccine Name | Covishield |
| Dose Number | 1 |
| Date Administered | 2026-08-15 |
| Administered By | Dr. Chathurika Jayasuriya |
| Center Name | Kurunegala Base Hospital |
| Certificate Number | CERT-2026-0001 |

Save it, then repeat for the second patient.

| Field | Value |
|---|---|
| Patient NIC | Select Nadeesha Wickramasinghe (200013456789) |
| Patient Name | Filled automatically |
| Vaccine Name | Pfizer BioNTech |
| Dose Number | 1 |
| Date Administered | 2026-08-16 |
| Administered By | Dr. Ruwan Bandara |
| Center Name | Kandy General Hospital |
| Certificate Number | CERT-2026-0002 |

### View

Confirm both dose records appear in the table.

### Update

Edit Kasun Perera's dose record, change Dose Number to `2`, and save.

### Delete

Try deleting a test dose record you do not need.

## 6. Adverse Reaction Monitoring and Incident Management

Go to **Adverse Reactions**. Patient NIC, Vaccine Name and Center Name are dropdowns here as well, and Patient Name auto fills the same way as in Dose Records.

### Create

Click **Report Adverse Reaction** and fill in the form.

| Field | Value |
|---|---|
| Patient NIC | Select Kasun Perera (199512345678) |
| Patient Name | Filled automatically |
| Vaccine Name | Covishield |
| Center Name | Kurunegala Base Hospital |
| Reaction Description | Mild fever and soreness at the injection site |
| Severity | MILD |
| Reported Date | 2026-08-16 |
| Action Taken | Advised rest and paracetamol, monitored for 24 hours |

### View

Confirm the record appears with a colored severity badge.

### Update

Edit the record, change Severity to `MODERATE`, and save. Confirm the badge color changes.

### Delete

Try deleting a test record you do not need.

## 7. Reporting and Analytics Management

Go to **Reports**. This module does not depend on other modules.

### Create

Click **New Report** and fill in the form.

| Field | Value |
|---|---|
| Report Title | Weekly Vaccination Summary, Week 33 |
| Report Type | GENERAL |
| Period Start | 2026-08-10 |
| Period End | 2026-08-16 |
| Generated By | Nimali Fernando |
| Generated Date | 2026-08-16 |
| Summary | Covered two vaccination centers with two doses administered and one adverse reaction reported |

### View

Confirm the report appears in the table with a truncated summary.

### Update

Edit the report, change Report Type to `ADVERSE_REACTIONS`, and save.

### Delete

Try deleting a test report you do not need.

## 8. Logout

Click **Logout** in the navigation. You should be redirected to the landing page, and visiting `/dashboard` directly should send you back to the login page until you sign in again.
