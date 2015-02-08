package org.eclipse.ice.client.widgets.jface;

import java.util.List;

import org.eclipse.swt.graphics.Image;

public interface ICellContentProvider {

	public static final char SECRET_CHAR = '*';
	public static final String NO_TEXT = "";
	
	/**
	 * Whether or not the specified element is valid for the cell.
	 */
	public boolean isValid(Object element);

	/**
	 * Whether the cell is enabled (grayed out or otherwise un-editable).
	 */
	public boolean isEnabled(Object element);

	/**
	 * Whether the cell should be visible.
	 */
	public boolean isVisible(Object element);

	/**
	 * Whether the contents of the cell should be obscured.
	 */
	public boolean isSecret(Object element);

	/**
	 * Whether the cell should be activated, e.g. checked or selected.
	 */
	public boolean isActive(Object element);

	public Object getValue(Object element);

	public List<String> getAllowedValues(Object element);

	public String getText(Object element);

	public String getToolTipText(Object element);

	public Image getImage(Object element);

	public boolean setValue(Object element, Object value);

	public CellType getCellType(Object element);

	public int[] getCellStyles(Object element);
}
