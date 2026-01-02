package model;

import java.util.LinkedHashMap;
import interfaces.FormModel;
import model.util.ParseUtil;

public class Staff implements FormModel {
    private String staffId;
    private String firstName;
    private String lastName;
    private String role;
    private String department;
    private String facilityId;
    private String phoneNumber;
    private String email;
    private String employmentStatus;
    private String startDate;
    private String lineManager;
    private String accessLevel;

    public Staff() {}

    public Staff(String staffId, String firstName, String lastName, String role, String department, String facilityId,
                 String phoneNumber, String email, String employmentStatus, String startDate, String lineManager, String accessLevel) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
        this.facilityId = facilityId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
        this.lineManager = lineManager;
        this.accessLevel = accessLevel;
    }

    public static Staff fromCsvRow(String[] r) {
        // staff_id, first_name, last_name, role, department, facility_id, phone_number, email, employment_status, start_date, line_manager, access_level
        Staff s = new Staff();
        s.staffId = get(r, 0);
        s.firstName = get(r, 1);
        s.lastName = get(r, 2);
        s.role = get(r, 3);
        s.department = get(r, 4);
        s.facilityId = get(r, 5);
        s.phoneNumber = get(r, 6);
        s.email = get(r, 7);
        s.employmentStatus = get(r, 8);
        s.startDate = get(r, 9);
        s.lineManager = get(r, 10);
        s.accessLevel = get(r, 11);
        return s;
    }

    private static String get(String[] r, int i) { return i < r.length ? ParseUtil.safe(r[i]) : ""; }

    @Override public String getId() { return staffId; }
    @Override public void setId(String id) { this.staffId = id; }

    @Override
    public LinkedHashMap<String, String> toFieldMap() {
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        m.put("staff_id", staffId);
        m.put("first_name", firstName);
        m.put("last_name", lastName);
        m.put("role", role);
        m.put("department", department);
        m.put("facility_id", facilityId);
        m.put("phone_number", phoneNumber);
        m.put("email", email);
        m.put("employment_status", employmentStatus);
        m.put("start_date", startDate);
        m.put("line_manager", lineManager);
        m.put("access_level", accessLevel);
        return m;
    }

    @Override
    public void updateFromFieldMap(LinkedHashMap<String, String> f) {
        staffId = ParseUtil.safe(f.get("staff_id"));
        firstName = ParseUtil.safe(f.get("first_name"));
        lastName = ParseUtil.safe(f.get("last_name"));
        role = ParseUtil.safe(f.get("role"));
        department = ParseUtil.safe(f.get("department"));
        facilityId = ParseUtil.safe(f.get("facility_id"));
        phoneNumber = ParseUtil.safe(f.get("phone_number"));
        email = ParseUtil.safe(f.get("email"));
        employmentStatus = ParseUtil.safe(f.get("employment_status"));
        startDate = ParseUtil.safe(f.get("start_date"));
        lineManager = ParseUtil.safe(f.get("line_manager"));
        accessLevel = ParseUtil.safe(f.get("access_level"));
    }

    @Override
    public FormModel copy() {
        return new Staff(staffId, firstName, lastName, role, department, facilityId, phoneNumber, email, employmentStatus,
                startDate, lineManager, accessLevel);
    }

    @Override
    public String[] toCsvRow() {
        return new String[] {
                staffId, firstName, lastName, role, department, facilityId, phoneNumber, email, employmentStatus,
                startDate, lineManager, accessLevel
        };
    }

    @Override
    public String[] headers() {
        return new String[] {
                "staff_id","first_name","last_name","role","department","facility_id","phone_number","email",
                "employment_status","start_date","line_manager","access_level"
        };
    }

    @Override
    public String toString() {
        return staffId + " - " + firstName + " " + lastName + " (" + role + ")";
    }
}
