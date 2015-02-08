package org.eclipse.ice.client.widgets.jface.table;

import java.util.List;

import org.eclipse.ice.client.widgets.jface.CellType;
import org.eclipse.ice.client.widgets.jface.ICellContentProvider;
import org.eclipse.swt.graphics.Image;

public class ColumnCellContentProvider implements ICellContentProvider {

	private final int column;
	private final ICellContentProvider contentProvider;

	public ColumnCellContentProvider(int column,
			ICellContentProvider parentContentProvider) {
		this.column = column;
		this.contentProvider = parentContentProvider;

		if (column < 0) {
			throw new IllegalArgumentException(
					"ColumnCellContentProvider error: "
							+ "Invalid index. Column index must be greater than or equal to 0.");
		} else if (parentContentProvider == null) {
			throw new NullPointerException("ColumnCellContentProvider error: "
					+ "Parent content provider must not be null");
		}

		return;
	}

	private Object getIndexedElement(Object element) {
		Object indexedElement = null;
		if (element != null && element instanceof List<?>) {
			List<?> list = (List<?>) element;
			if (column < list.size()) {
				indexedElement = list.get(column);
			}
		}
		return indexedElement;
	}

	@Override
	public boolean isValid(Object element) {
		return contentProvider.isValid(getIndexedElement(element));
	}

	@Override
	public boolean isEnabled(Object element) {
		return contentProvider.isEnabled(getIndexedElement(element));
	}

	@Override
	public boolean isVisible(Object element) {
		return contentProvider.isVisible(getIndexedElement(element));
	}

	@Override
	public boolean isSecret(Object element) {
		return contentProvider.isSecret(getIndexedElement(element));
	}

	@Override
	public boolean isActive(Object element) {
		return contentProvider.isActive(getIndexedElement(element));
	}

	@Override
	public Object getValue(Object element) {
		return contentProvider.getValue(getIndexedElement(element));
	}

	@Override
	public List<String> getAllowedValues(Object element) {
		return contentProvider.getAllowedValues(getIndexedElement(element));
	}

	@Override
	public String getText(Object element) {
		return contentProvider.getText(getIndexedElement(element));
	}

	@Override
	public String getToolTipText(Object element) {
		return contentProvider.getToolTipText(getIndexedElement(element));
	}

	@Override
	public Image getImage(Object element) {
		return contentProvider.getImage(getIndexedElement(element));
	}

	@Override
	public boolean setValue(Object element, Object value) {
		return contentProvider.setValue(getIndexedElement(element), value);
	}

	@Override
	public CellType getCellType(Object element) {
		return contentProvider.getCellType(getIndexedElement(element));
	}

	@Override
	public int[] getCellStyles(Object element) {
		return contentProvider.getCellStyles(getIndexedElement(element));
	}

}
