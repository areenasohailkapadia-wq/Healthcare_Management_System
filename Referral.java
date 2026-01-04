package model;

import java.util.LinkedHashMap;
import interfaces.FormModel;
import model.util.ParseUtil;

public class Referral implements FormModel {
    private String referralId;
    private String patientId;
    private String referringClinicianId;
    private String referredToClinicianId;
    private String referringFacilityId;
    private String referredToFacilityId;
    private String referralDate;
    private String urgencyLevel;
    private String referralReason;
    private String clinicalSummary;
    private String requestedInvestigations;
    private String status;
    private String appointmentId;
    private String notes;
    private String createdDate;
    private String lastUpdated;

    public Referral() {}

    public Referral(String referralId, String patientId, String referringClinicianId, String referredToClinicianId,
                    String referringFacilityId, String referredToFacilityId, String referralDate, String urgencyLevel,
                    String referralReason, String clinicalSummary, String requestedInvestigations, String status,
                    String appointmentId, String notes, String createdDate, String lastUpdated) {
        this.referralId = referralId;
        this.patientId = patientId;
        this.referringClinicianId = referringClinicianId;
        this.referredToClinicianId = referredToClinicianId;
        this.referringFacilityId = referringFacilityId;
        this.referredToFacilityId = referredToFacilityId;
        this.referralDate = referralDate;
        this.urgencyLevel = urgencyLevel;
        this.referralReason = referralReason;
        this.clinicalSummary = clinicalSummary;
        this.requestedInvestigations = requestedInvestigations;
        this.status = status;
        this.appointmentId = appointmentId;
        this.notes = notes;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }

    public static Referral fromCsvRow(String[] r) {
        // referral_id, patient_id, referring_clinician_id, referred_to_clinician_id, referring_facility_id, referred_to_facility_id,
        // referral_date, urgency_level, referral_reason, clinical_summary, requested_investigations, status, appointment_id, notes, created_date, last_updated
        Referral ref = new Referral();
        ref.referralId = get(r, 0);
        ref.patientId = get(r, 1);
        ref.referringClinicianId = get(r, 2);
        ref.referredToClinicianId = get(r, 3);
        ref.referringFacilityId = get(r, 4);
        ref.referredToFacilityId = get(r, 5);
        ref.referralDate = get(r, 6);
        ref.urgencyLevel = get(r, 7);
        ref.referralReason = get(r, 8);
        ref.clinicalSummary = get(r, 9);
        ref.requestedInvestigations = get(r, 10);
        ref.status = get(r, 11);
        ref.appointmentId = get(r, 12);
        ref.notes = get(r, 13);
        ref.createdDate = get(r, 14);
        ref.lastUpdated = get(r, 15);
        return ref;
    }

    private static String get(String[] r, int i) { return i < r.length ? ParseUtil.safe(r[i]) : ""; }

    @Override public String getId() { return referralId; }
    @Override public void setId(String id) { this.referralId = id; }

    public String getReferralId() { return referralId; }
    public String getPatientId() { return patientId; }
    public String getReferringClinicianId() { return referringClinicianId; }
    public String getReferredToClinicianId() { return referredToClinicianId; }
    public String getReferringFacilityId() { return referringFacilityId; }
    public String getReferredToFacilityId() { return referredToFacilityId; }

    @Override
    public LinkedHashMap<String, String> toFieldMap() {
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        m.put("referral_id", referralId);
        m.put("patient_id", patientId);
        m.put("referring_clinician_id", referringClinicianId);
        m.put("referred_to_clinician_id", referredToClinicianId);
        m.put("referring_facility_id", referringFacilityId);
        m.put("referred_to_facility_id", referredToFacilityId);
        m.put("referral_date", referralDate);
        m.put("urgency_level", urgencyLevel);
        m.put("referral_reason", referralReason);
        m.put("clinical_summary", clinicalSummary);
        m.put("requested_investigations", requestedInvestigations);
        m.put("status", status);
        m.put("appointment_id", appointmentId);
        m.put("notes", notes);
        m.put("created_date", createdDate);
        m.put("last_updated", lastUpdated);
        return m;
    }

    @Override
    public void updateFromFieldMap(LinkedHashMap<String, String> f) {
        referralId = ParseUtil.safe(f.get("referral_id"));
        patientId = ParseUtil.safe(f.get("patient_id"));
        referringClinicianId = ParseUtil.safe(f.get("referring_clinician_id"));
        referredToClinicianId = ParseUtil.safe(f.get("referred_to_clinician_id"));
        referringFacilityId = ParseUtil.safe(f.get("referring_facility_id"));
        referredToFacilityId = ParseUtil.safe(f.get("referred_to_facility_id"));
        referralDate = ParseUtil.safe(f.get("referral_date"));
        urgencyLevel = ParseUtil.safe(f.get("urgency_level"));
        referralReason = ParseUtil.safe(f.get("referral_reason"));
        clinicalSummary = ParseUtil.safe(f.get("clinical_summary"));
        requestedInvestigations = ParseUtil.safe(f.get("requested_investigations"));
        status = ParseUtil.safe(f.get("status"));
        appointmentId = ParseUtil.safe(f.get("appointment_id"));
        notes = ParseUtil.safe(f.get("notes"));
        createdDate = ParseUtil.safe(f.get("created_date"));
        lastUpdated = ParseUtil.safe(f.get("last_updated"));
    }

    @Override
    public FormModel copy() {
        return new Referral(referralId, patientId, referringClinicianId, referredToClinicianId, referringFacilityId,
                referredToFacilityId, referralDate, urgencyLevel, referralReason, clinicalSummary, requestedInvestigations,
                status, appointmentId, notes, createdDate, lastUpdated);
    }

    @Override
    public String[] toCsvRow() {
        return new String[] {
                referralId, patientId, referringClinicianId, referredToClinicianId, referringFacilityId, referredToFacilityId,
                referralDate, urgencyLevel, referralReason, clinicalSummary, requestedInvestigations, status, appointmentId,
                notes, createdDate, lastUpdated
        };
    }

    @Override
    public String[] headers() {
        return new String[] {
                "referral_id","patient_id","referring_clinician_id","referred_to_clinician_id","referring_facility_id",
                "referred_to_facility_id","referral_date","urgency_level","referral_reason","clinical_summary",
                "requested_investigations","status","appointment_id","notes","created_date","last_updated"
        };
    }

    @Override
    public String toString() {
        return referralId + " (" + urgencyLevel + ")";
    }
}
