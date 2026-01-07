package interfaces;

import java.util.LinkedHashMap;

public interface FormModel extends Identifiable {
    /**
     * Field map is used by the generic Swing forms.
     * Keys must be consistent with CSV headers for the entity.
     */
    LinkedHashMap<String, String> toFieldMap();
    void updateFromFieldMap(LinkedHashMap<String, String> fields);

    /** Deep copy used by edit dialogs so changes can be cancelled safely. */
    FormModel copy();

    /** CSV serialization (order must match headers()). */
    String[] toCsvRow();

    /** Headers for CSV write/read. */
    String[] headers();
}
