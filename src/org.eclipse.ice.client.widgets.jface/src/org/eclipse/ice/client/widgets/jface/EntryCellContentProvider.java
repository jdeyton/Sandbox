package org.eclipse.ice.client.widgets.jface;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ice.datastructures.form.AllowedValueType;
import org.eclipse.ice.datastructures.form.Entry;
import org.eclipse.swt.graphics.Image;

public class EntryCellContentProvider implements ICellContentProvider {

	@Override
	public boolean isValid(Object element) {
		return element != null && element instanceof Entry;
	}

	@Override
	public boolean isEnabled(Object element) {
		return isValid(element);
	}

	@Override
	public boolean isVisible(Object element) {
		return isValid(element) && ((Entry) element).isReady();
	}

	@Override
	public boolean isSecret(Object element) {
		return isValid(element) && ((Entry) element).isSecret();
	}

	@Override
	public boolean isActive(Object element) {
		return false;
	}

	@Override
	public Object getValue(Object element) {
		return (isValid(element) ? ((Entry) element).getValue() : null);
	}

	@Override
	public List<String> getAllowedValues(Object element) {
		return (isValid(element) ? ((Entry) element).getAllowedValues()
				: new ArrayList<String>());
	}

	@Override
	public String getText(Object element) {
		Object value;
		String text = NO_TEXT;

		if (isValid(element)) {
			value = getValue(element);
			// The default value is the empty string if the underlying Entry
			// value is null.
			text = "";
			// Get the Entry's value in string form.
			if (value != null) {
				text = value.toString();
				// If necessary, obscure the text.
				if (isSecret(element)) {
					text = text.replaceAll("(?s).",
							Character.toString(SECRET_CHAR));
				}
			}
		}

		return text;
	}

	@Override
	public String getToolTipText(Object element) {
		return getText(element);
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public boolean setValue(Object element, Object value) {
		boolean changed = false;
		if (isValid(element) && value != null) {
			changed = ((Entry) element).setValue(value.toString());
		}
		return changed;
	}

	@Override
	public CellType getCellType(Object element) {
		CellType type = CellType.Text;

		if (isValid(element)) {
			Entry entry = (Entry) element;
			AllowedValueType entryType = entry.getValueType();
			if (entryType == AllowedValueType.Discrete) {
				type = CellType.Combo;
			} else if (entryType == AllowedValueType.File) {
				if (entry.getAllowedValues().isEmpty()) {
					type = CellType.File;
				} else {
					type = CellType.ComboFile;
				}
			}
		}

		return type;
	}

}
