package org.eclipse.ice.client.widgets.jface;

import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class CellEditingSupport extends EditingSupport {

	protected final ICellContentProvider contentProvider;

	private TextCellEditor textCellEditor;
	private ComboBoxViewerCellEditor comboCellEditor;
	private ButtonCellEditor buttonCellEditor;
	private FileCellEditor fileCellEditor;
	private ComboFileCellEditor comboFileCellEditor;

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
				// Since there is a JFace viewer here, we have to set its input.
				((ComboBoxViewerCellEditor) editor).setInput(element);
			} else if (type == CellType.Button) {
				editor = getButtonCellEditor();
			} else if (type == CellType.File) {
				editor = getFileCellEditor();
			} else if (type == CellType.ComboFile) {
				editor = getComboFileCellEditor();
				// Since there is a JFace viewer here, we have to set its input.
				((ComboBoxViewerCellEditor) editor).setInput(element);
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
			// The underlying ComboViewer must have a JFace content provider.
			comboCellEditor.setContentProvider(createComboContentProvider());
		}
		return comboCellEditor;
	}

	protected ButtonCellEditor getButtonCellEditor() {
		if (buttonCellEditor == null) {
			Composite parent = (Composite) getViewer().getControl();
			buttonCellEditor = new ButtonCellEditor(parent, SWT.CHECK);
		}
		return buttonCellEditor;
	}

	protected FileCellEditor getFileCellEditor() {
		if (fileCellEditor == null) {
			Composite parent = (Composite) getViewer().getControl();
			fileCellEditor = new FileCellEditor(parent);
		}
		return fileCellEditor;
	}

	protected ComboFileCellEditor getComboFileCellEditor() {
		if (comboFileCellEditor == null) {
			Composite parent = (Composite) getViewer().getControl();
			comboFileCellEditor = new ComboFileCellEditor(parent);
			// The underlying ComboViewer must have a JFace content provider.
			comboFileCellEditor
					.setContentProvider(createComboContentProvider());
		}
		return comboFileCellEditor;
	}

	/**
	 * Creates a content provider for underlying JFace viewers. For these
	 * viewers, it is assumed that the input element is the same type of element
	 * managed by the cell's {@link #contentProvider}. Thus, the created content
	 * provider redirects any applicable methods to the cell's
	 * {@link #contentProvider}.
	 * 
	 * @return A new {@code IStructuredContentProvider} that references the
	 *         {@link #contentProvider}.
	 */
	private IStructuredContentProvider createComboContentProvider() {
		return new IStructuredContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
				// Nothing to do.
			}

			@Override
			public void dispose() {
				// Nothing to do.
			}

			@Override
			public Object[] getElements(Object inputElement) {
				List<String> allowedValues = contentProvider
						.getAllowedValues(inputElement);
				return allowedValues.toArray(new Object[allowedValues.size()]);
			}
		};
	}
}
