package org.eclipse.ice.client.widgets.jface;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class CellEditingSupport extends EditingSupport {

	protected final ICellContentProvider contentProvider;

	private TextCellEditor textCellEditor;
	private ComboBoxViewerCellEditor comboCellEditor;
	private CellEditor buttonCellEditor;
	private CellEditor fileCellEditor;
	private CellEditor comboFileCellEditor;

	public CellEditingSupport(ColumnViewer viewer,
			ICellContentProvider contentProvider) {
		super(viewer);

		this.contentProvider = contentProvider;

		// Throw an error if the content provider is null.
		if (contentProvider == null) {
			throw new NullPointerException("CellEditingSupport error: "
					+ "Cannot accept null ICellContentProvider.");
		}

		return;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		CellEditor editor = null;

		if (contentProvider.isValid(element)) {
			CellType type = contentProvider.getCellType(element);

			if (type == CellType.Text) {
				editor = getTextCellEditor();
			} else if (type == CellType.Combo) {
				editor = getComboCellEditor();
			} else if (type == CellType.Button) {
				editor = getButtonCellEditor();
			} else if (type == CellType.File) {
				editor = getFileCellEditor();
			} else if (type == CellType.ComboFile) {
				editor = getComboFileCellEditor();
			} else {
				editor = getTextCellEditor();
			}
		}

		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return contentProvider.isEnabled(element);
	}

	@Override
	protected Object getValue(Object element) {
		// TODO How to handle combos?
		return contentProvider.getValue(element);
	}

	@Override
	protected void setValue(Object element, Object value) {
		if (contentProvider.setValue(element, value)) {
			// Force the viewer to refresh for this specific element. This means
			// the change will be reflected in the viewer.
			getViewer().update(element, null);
		}
	}

	protected TextCellEditor getTextCellEditor() {
		if (textCellEditor == null) {
			Composite parent = (Composite) getViewer().getControl();
			textCellEditor = new TextCellEditor(parent, SWT.RIGHT);
		}
		return textCellEditor;
	}

	protected ComboBoxViewerCellEditor getComboCellEditor() {
		if (comboCellEditor == null) {
			Composite parent = (Composite) getViewer().getControl();
			comboCellEditor = new ComboBoxViewerCellEditor(parent,
					SWT.DROP_DOWN | SWT.READ_ONLY);
		}
		return comboCellEditor;
	}

	protected CellEditor getButtonCellEditor() {
		if (buttonCellEditor == null) {
			// TODO
		}
		return buttonCellEditor;
	}

	protected CellEditor getFileCellEditor() {
		if (fileCellEditor == null) {
			// TODO
		}
		return fileCellEditor;
	}

	protected CellEditor getComboFileCellEditor() {
		if (comboFileCellEditor == null) {
			// TODO
		}
		return comboFileCellEditor;
	}
}
