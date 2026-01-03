package model;

import java.util.LinkedHashMap;
import interfaces.FormModel;
import model.util.ParseUtil;

public class Facility implements FormModel {
    private String facilityId;
    private String facilityName;
    private String facilityType;
    private String address;
    private String postcode;
    private String phoneNumber;
    private String email;
    private String openingHours;
    private String managerName;
    private int capacity;
    private String specialitiesOffered;

    public Facility() {}

    public Facility(String facilityId, String facilityName, String facilityType, String address, String postcode,
                    String phoneNumber, String email, String openingHours, String managerName, int capacity,
                    String specialitiesOffered) {
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.facilityType = facilityType;
        this.address = address;
        this.postcode = postcode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.openingHours = openingHours;
        this.managerName = managerName;
        this.capacity = capacity;
        this.specialitiesOffered = specialitiesOffered;
    }

    public static Facility fromCsvRow(String[] r) {
        // facility_id, facility_name, facility_type, address, postcode, phone_number, email, opening_hours, manager_name, capacity, specialities_offered
        Facility f = new Facility();
        f.facilityId = get(r, 0);
        f.facilityName = get(r, 1);
        f.facilityType = get(r, 2);
        f.address = get(r, 3);
        f.postcode = get(r, 4);
        f.phoneNumber = get(r, 5);
        f.email = get(r, 6);
        f.openingHours = get(r, 7);
        f.managerName = get(r, 8);
        f.capacity = ParseUtil.toInt(get(r, 9), 0);
        f.specialitiesOffered = get(r, 10);
        return f;
    }

    private static String get(String[] r, int i) { return i < r.length ? ParseUtil.safe(r[i]) : ""; }

    @Override public String getId() { return facilityId; }
    @Override public void setId(String id) { this.facilityId = id; }

    public String getFacilityId() { return facilityId; }
    public String getFacilityName() { return facilityName; }
    public String getFacilityType() { return facilityType; }

    @Override
    public LinkedHashMap<String, String> toFieldMap() {
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        m.put("facility_id", facilityId);
        m.put("facility_name", facilityName);
        m.put("facility_type", facilityType);
        m.put("address", address);
        m.put("postcode", postcode);
        m.put("phone_number", phoneNumber);
        m.put("email", email);
        m.put("opening_hours", openingHours);
        m.put("manager_name", managerName);
        m.put("capacity", String.valueOf(capacity));
        m.put("specialities_offered", specialitiesOffered);
        return m;
    }

    @Override
    public void updateFromFieldMap(LinkedHashMap<String, String> f) {
        facilityId = ParseUtil.safe(f.get("facility_id"));
        facilityName = ParseUtil.safe(f.get("facility_name"));
        facilityType = ParseUtil.safe(f.get("facility_type"));
        address = ParseUtil.safe(f.get("address"));
        postcode = ParseUtil.safe(f.get("postcode"));
        phoneNumber = ParseUtil.safe(f.get("phone_number"));
        email = ParseUtil.safe(f.get("email"));
        openingHours = ParseUtil.safe(f.get("opening_hours"));
        managerName = ParseUtil.safe(f.get("manager_name"));
        capacity = ParseUtil.toInt(f.get("capacity"), 0);
        specialitiesOffered = ParseUtil.safe(f.get("specialities_offered"));
    }

    @Override
    public FormModel copy() {
        return new Facility(facilityId, facilityName, facilityType, address, postcode, phoneNumber, email,
                openingHours, managerName, capacity, specialitiesOffered);
    }

    @Override
    public String[] toCsvRow() {
        return new String[] { facilityId, facilityName, facilityType, address, postcode, phoneNumber, email,
                openingHours, managerName, String.valueOf(capacity), specialitiesOffered };
    }

    @Override
    public String[] headers() {
        return new String[] {
                "facility_id","facility_name","facility_type","address","postcode","phone_number","email",
                "opening_hours","manager_name","capacity","specialities_offered"
        };
    }

    @Override
    public String toString() {
        return facilityId + " - " + facilityName;
    }
}
