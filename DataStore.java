package model;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory storage for all entities.
 * No database is used (assignment requirement).
 */
public class DataStore {
    private final List<Patient> patients = new ArrayList<>();
    private final List<Clinician> clinicians = new ArrayList<>();
    private final List<Facility> facilities = new ArrayList<>();
    private final List<Appointment> appointments = new ArrayList<>();
    private final List<Prescription> prescriptions = new ArrayList<>();
    private final List<Referral> referrals = new ArrayList<>();
    private final List<Staff> staff = new ArrayList<>();

    public List<Patient> getPatients() { return patients; }
    public List<Clinician> getClinicians() { return clinicians; }
    public List<Facility> getFacilities() { return facilities; }
    public List<Appointment> getAppointments() { return appointments; }
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public List<Referral> getReferrals() { return referrals; }
    public List<Staff> getStaff() { return staff; }

    public void clearAll() {
        patients.clear();
        clinicians.clear();
        facilities.clear();
        appointments.clear();
        prescriptions.clear();
        referrals.clear();
        staff.clear();
    }
}
