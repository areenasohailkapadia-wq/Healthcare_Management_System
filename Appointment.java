package model;

import java.util.LinkedHashMap;
import interfaces.FormModel;
import model.util.ParseUtil;

public class Appointment implements FormModel {
    private String appointmentId;
    private String patientId;
    private String clinicianId;
    private String facilityId;
    private String appointmentDate;
    private String appointmentTime;
    private int durationMinutes;
    private String appointmentType;
    private String status;
    private String reasonForVisit;
    private String notes;
    private String createdDate;
    private String lastModified;

    public Appointment() {}

    public Appointment(String appointmentId, String patientId, String clinicianId, String facilityId,
                       String appointmentDate, String appointmentTime, int durationMinutes, String appointmentType,
                       String status, String reasonForVisit, String notes, String createdDate, String lastModified) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.facilityId = facilityId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.durationMinutes = durationMinutes;
        this.appointmentType = appointmentType;
        this.status = status;
        this.reasonForVisit = reasonForVisit;
        this.notes = notes;
        this.createdDate = createdDate;
        this.lastModified = lastModified;
    }

    public static Appointment fromCsvRow(String[] r) {
        // appointment_id, patient_id, clinician_id, facility_id, appointment_date, appointment_time, duration_minutes,
        // appointment_type, status, reason_for_visit, notes, created_date, last_modified
        Appointment a = new Appointment();
        a.appointmentId = get(r, 0);
        a.patientId = get(r, 1);
        a.clinicianId = get(r, 2);
        a.facilityId = get(r, 3);
        a.appointmentDate = get(r, 4);
        a.appointmentTime = get(r, 5);
        a.durationMinutes = ParseUtil.toInt(get(r, 6), 0);
        a.appointmentType = get(r, 7);
        a.status = get(r, 8);
        a.reasonForVisit = get(r, 9);
        a.notes = get(r, 10);
        a.createdDate = get(r, 11);
        a.lastModified = get(r, 12);
        return a;
    }

    private static String get(String[] r, int i) { return i < r.length ? ParseUtil.safe(r[i]) : ""; }

    @Override public String getId() { return appointmentId; }
    @Override public void setId(String id) { this.appointmentId = id; }

    public String getAppointmentId() { return appointmentId; }
    public String getPatientId() { return patientId; }
    public String getClinicianId() { return clinicianId; }
    public String getFacilityId() { return facilityId; }

    @Override
    public LinkedHashMap<String, String> toFieldMap() {
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        m.put("appointment_id", appointmentId);
        m.put("patient_id", patientId);
        m.put("clinician_id", clinicianId);
        m.put("facility_id", facilityId);
        m.put("appointment_date", appointmentDate);
        m.put("appointment_time", appointmentTime);
        m.put("duration_minutes", String.valueOf(durationMinutes));
        m.put("appointment_type", appointmentType);
        m.put("status", status);
        m.put("reason_for_visit", reasonForVisit);
        m.put("notes", notes);
        m.put("created_date", createdDate);
        m.put("last_modified", lastModified);
        return m;
    }

    @Override
    public void updateFromFieldMap(LinkedHashMap<String, String> f) {
        appointmentId = ParseUtil.safe(f.get("appointment_id"));
        patientId = ParseUtil.safe(f.get("patient_id"));
        clinicianId = ParseUtil.safe(f.get("clinician_id"));
        facilityId = ParseUtil.safe(f.get("facility_id"));
        appointmentDate = ParseUtil.safe(f.get("appointment_date"));
        appointmentTime = ParseUtil.safe(f.get("appointment_time"));
        durationMinutes = ParseUtil.toInt(f.get("duration_minutes"), 0);
        appointmentType = ParseUtil.safe(f.get("appointment_type"));
        status = ParseUtil.safe(f.get("status"));
        reasonForVisit = ParseUtil.safe(f.get("reason_for_visit"));
        notes = ParseUtil.safe(f.get("notes"));
        createdDate = ParseUtil.safe(f.get("created_date"));
        lastModified = ParseUtil.safe(f.get("last_modified"));
    }

    @Override
    public FormModel copy() {
        return new Appointment(appointmentId, patientId, clinicianId, facilityId, appointmentDate, appointmentTime,
                durationMinutes, appointmentType, status, reasonForVisit, notes, createdDate, lastModified);
    }

    @Override
    public String[] toCsvRow() {
        return new String[] {
                appointmentId, patientId, clinicianId, facilityId, appointmentDate, appointmentTime,
                String.valueOf(durationMinutes), appointmentType, status, reasonForVisit, notes, createdDate, lastModified
        };
    }

    @Override
    public String[] headers() {
        return new String[] {
                "appointment_id","patient_id","clinician_id","facility_id","appointment_date","appointment_time",
                "duration_minutes","appointment_type","status","reason_for_visit","notes","created_date","last_modified"
        };
    }

    @Override
    public String toString() {
        return appointmentId + " (" + appointmentDate + " " + appointmentTime + ")";
    }
}
