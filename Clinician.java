package model;

import java.util.LinkedHashMap;
import interfaces.FormModel;
import model.util.ParseUtil;

public class Clinician implements FormModel {
    private String clinicianId;
    private String firstName;
    private String lastName;
    private String title;
    private String speciality;
    private String gmcNumber;
    private String phoneNumber;
    private String email;
    private String workplaceId;
    private String workplaceType;
    private String employmentStatus;
    private String startDate;

    public Clinician() {}

    public Clinician(String clinicianId, String firstName, String lastName, String title, String speciality,
                     String gmcNumber, String phoneNumber, String email, String workplaceId, String workplaceType,
                     String employmentStatus, String startDate) {
        this.clinicianId = clinicianId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.speciality = speciality;
        this.gmcNumber = gmcNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.workplaceId = workplaceId;
        this.workplaceType = workplaceType;
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
    }

    public static Clinician fromCsvRow(String[] r) {
        // clinician_id, first_name, last_name, title, speciality, gmc_number, phone_number, email, workplace_id, workplace_type, employment_status, start_date
        Clinician c = new Clinician();
        c.clinicianId = get(r, 0);
        c.firstName = get(r, 1);
        c.lastName = get(r, 2);
        c.title = get(r, 3);
        c.speciality = get(r, 4);
        c.gmcNumber = get(r, 5);
        c.phoneNumber = get(r, 6);
        c.email = get(r, 7);
        c.workplaceId = get(r, 8);
        c.workplaceType = get(r, 9);
        c.employmentStatus = get(r, 10);
        c.startDate = get(r, 11);
        return c;
    }

    private static String get(String[] r, int i) { return i < r.length ? ParseUtil.safe(r[i]) : ""; }

    @Override public String getId() { return clinicianId; }
    @Override public void setId(String id) { this.clinicianId = id; }

    public String getClinicianId() { return clinicianId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getTitle() { return title; }
    public String getSpeciality() { return speciality; }
    public String getEmail() { return email; }

    @Override
    public LinkedHashMap<String, String> toFieldMap() {
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        m.put("clinician_id", clinicianId);
        m.put("first_name", firstName);
        m.put("last_name", lastName);
        m.put("title", title);
        m.put("speciality", speciality);
        m.put("gmc_number", gmcNumber);
        m.put("phone_number", phoneNumber);
        m.put("email", email);
        m.put("workplace_id", workplaceId);
        m.put("workplace_type", workplaceType);
        m.put("employment_status", employmentStatus);
        m.put("start_date", startDate);
        return m;
    }

    @Override
    public void updateFromFieldMap(LinkedHashMap<String, String> f) {
        clinicianId = ParseUtil.safe(f.get("clinician_id"));
        firstName = ParseUtil.safe(f.get("first_name"));
        lastName = ParseUtil.safe(f.get("last_name"));
        title = ParseUtil.safe(f.get("title"));
        speciality = ParseUtil.safe(f.get("speciality"));
        gmcNumber = ParseUtil.safe(f.get("gmc_number"));
        phoneNumber = ParseUtil.safe(f.get("phone_number"));
        email = ParseUtil.safe(f.get("email"));
        workplaceId = ParseUtil.safe(f.get("workplace_id"));
        workplaceType = ParseUtil.safe(f.get("workplace_type"));
        employmentStatus = ParseUtil.safe(f.get("employment_status"));
        startDate = ParseUtil.safe(f.get("start_date"));
    }

    @Override
    public FormModel copy() {
        return new Clinician(clinicianId, firstName, lastName, title, speciality, gmcNumber, phoneNumber, email,
                workplaceId, workplaceType, employmentStatus, startDate);
    }

    @Override
    public String[] toCsvRow() {
        return new String[] { clinicianId, firstName, lastName, title, speciality, gmcNumber, phoneNumber, email,
                workplaceId, workplaceType, employmentStatus, startDate };
    }

    @Override
    public String[] headers() {
        return new String[] {
                "clinician_id","first_name","last_name","title","speciality","gmc_number","phone_number","email",
                "workplace_id","workplace_type","employment_status","start_date"
        };
    }

    @Override
    public String toString() {
        return clinicianId + " - " + title + " " + firstName + " " + lastName;
    }
}
