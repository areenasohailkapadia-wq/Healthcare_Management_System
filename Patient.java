package model;

import java.util.LinkedHashMap;
import interfaces.FormModel;
import model.util.ParseUtil;

public class Patient implements FormModel {
    private String patientId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String nhsNumber;
    private String gender;
    private String phoneNumber;
    private String email;
    private String address;
    private String postcode;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String registrationDate;
    private String gpSurgeryId;

    public Patient() {}

    public Patient(String patientId, String firstName, String lastName, String dateOfBirth, String nhsNumber,
                   String gender, String phoneNumber, String email, String address, String postcode,
                   String emergencyContactName, String emergencyContactPhone, String registrationDate, String gpSurgeryId) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.nhsNumber = nhsNumber;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.registrationDate = registrationDate;
        this.gpSurgeryId = gpSurgeryId;
    }

    public static Patient fromCsvRow(String[] r) {
        // patient_id, first_name, last_name, date_of_birth, nhs_number, gender, phone_number, email, address, postcode,
        // emergency_contact_name, emergency_contact_phone, registration_date, gp_surgery_id
        Patient p = new Patient();
        p.patientId = get(r, 0);
        p.firstName = get(r, 1);
        p.lastName = get(r, 2);
        p.dateOfBirth = get(r, 3);
        p.nhsNumber = get(r, 4);
        p.gender = get(r, 5);
        p.phoneNumber = get(r, 6);
        p.email = get(r, 7);
        p.address = get(r, 8);
        p.postcode = get(r, 9);
        p.emergencyContactName = get(r, 10);
        p.emergencyContactPhone = get(r, 11);
        p.registrationDate = get(r, 12);
        p.gpSurgeryId = get(r, 13);
        return p;
    }

    private static String get(String[] r, int i) { return i < r.length ? ParseUtil.safe(r[i]) : ""; }

    @Override public String getId() { return patientId; }
    @Override public void setId(String id) { this.patientId = id; }

    public String getPatientId() { return patientId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    @Override
    public LinkedHashMap<String, String> toFieldMap() {
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        m.put("patient_id", patientId);
        m.put("first_name", firstName);
        m.put("last_name", lastName);
        m.put("date_of_birth", dateOfBirth);
        m.put("nhs_number", nhsNumber);
        m.put("gender", gender);
        m.put("phone_number", phoneNumber);
        m.put("email", email);
        m.put("address", address);
        m.put("postcode", postcode);
        m.put("emergency_contact_name", emergencyContactName);
        m.put("emergency_contact_phone", emergencyContactPhone);
        m.put("registration_date", registrationDate);
        m.put("gp_surgery_id", gpSurgeryId);
        return m;
    }

    @Override
    public void updateFromFieldMap(LinkedHashMap<String, String> f) {
        patientId = ParseUtil.safe(f.get("patient_id"));
        firstName = ParseUtil.safe(f.get("first_name"));
        lastName = ParseUtil.safe(f.get("last_name"));
        dateOfBirth = ParseUtil.safe(f.get("date_of_birth"));
        nhsNumber = ParseUtil.safe(f.get("nhs_number"));
        gender = ParseUtil.safe(f.get("gender"));
        phoneNumber = ParseUtil.safe(f.get("phone_number"));
        email = ParseUtil.safe(f.get("email"));
        address = ParseUtil.safe(f.get("address"));
        postcode = ParseUtil.safe(f.get("postcode"));
        emergencyContactName = ParseUtil.safe(f.get("emergency_contact_name"));
        emergencyContactPhone = ParseUtil.safe(f.get("emergency_contact_phone"));
        registrationDate = ParseUtil.safe(f.get("registration_date"));
        gpSurgeryId = ParseUtil.safe(f.get("gp_surgery_id"));
    }

    @Override
    public FormModel copy() {
        return new Patient(patientId, firstName, lastName, dateOfBirth, nhsNumber, gender, phoneNumber, email,
                address, postcode, emergencyContactName, emergencyContactPhone, registrationDate, gpSurgeryId);
    }

    @Override
    public String[] toCsvRow() {
        return new String[] { patientId, firstName, lastName, dateOfBirth, nhsNumber, gender, phoneNumber, email,
                address, postcode, emergencyContactName, emergencyContactPhone, registrationDate, gpSurgeryId };
    }

    @Override
    public String[] headers() {
        return new String[] {
                "patient_id","first_name","last_name","date_of_birth","nhs_number","gender","phone_number","email",
                "address","postcode","emergency_contact_name","emergency_contact_phone","registration_date","gp_surgery_id"
        };
    }

    @Override
    public String toString() {
        return patientId + " - " + firstName + " " + lastName;
    }
}
