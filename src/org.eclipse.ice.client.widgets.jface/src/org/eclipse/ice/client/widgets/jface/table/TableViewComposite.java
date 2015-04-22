package org.eclipse.ice.client.widgets.jface.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ice.datastructures.form.TableComponent;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class TableViewComposite extends Composite {

	/**
	 * The current {@code TableComponent} data model that is rendered in the
	 * {@link #tableViewer}.
	 */
	private TableComponent tableComponent;

	/**
	 * The JFace {@code Viewer} that displays the {@link #tableComponent}'s rows
	 * as rows in an SWT {@link Table}.
	 */
	private TableViewer tableViewer;

	/**
	 * Adds a new, default row to the {@link #tableComponent}.
	 */
	private Action addRowAction;
	/**
	 * Removes all selected rows from the {@link #tableComponent}.
	 */
	private Action removeRowAction;

	/**
	 * A list to keep track of selected indices in the {@code TableComponent}.
	 */
	private final List<Integer> selectedIndices = new ArrayList<Integer>();

	/**
	 * The default constructor.
	 * 
	 * @param parent
	 *            a widget which will be the parent of the new instance (cannot
	 *            be null)
	 * @param style
	 *            the style of widget to construct
	 */
	public TableViewComposite(Composite parent, int style) {
		super(parent, style);

		// Use a 2-column GridLayout. The table should get all excess space and
		// should be on the left. The action buttons should be on the right.
		setLayout(new GridLayout(2, false));

		// Create the TableViewer.
		tableViewer = createTableViewer(this);
		tableViewer.getTable().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true));
		// Add a listener to match the TableViewer's selection events to the
		// list of selected rows indices.
		createTableViewerListeners();

		// Initialize the Actions.
		createActions();
		// Create a Composite to contain the buttons. Use a vertical fill
		// layout.
		Composite buttons = new Composite(this, SWT.NONE);
		buttons.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false,
				false));
		buttons.setLayout(new FillLayout(SWT.VERTICAL));
		// Create Buttons from the JFace Actions.
		new ActionContributionItem(addRowAction).fill(buttons);
		new ActionContributionItem(removeRowAction).fill(buttons);

		return;
	}

	/**
	 * Sets the current {@code TableComponent} data model that is rendered in
	 * the {@link #tableViewer}.
	 * 
	 * @param table
	 *            The new {@code TableComponent}. This value must not be
	 *            {@code null} and must have a row template.
	 */
	public void setTableComponent(TableComponent table) {

		// We cannot handle TableComponents with no template set.
		if (table == null || table.getRowTemplate() == null) {
			return;
		}
		// Otherwise, we should be able to add rows based on the set template.
		tableComponent = table;

		// Update the viewer's input.
		getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				// Make sure the remove action is disabled since the
				// TableComponent is new.
				removeRowAction.setEnabled(false);
				selectedIndices.clear();
				// Set the TableViewer's input.
				tableViewer.setInput(tableComponent);
			}
		});

		return;
	}

	/**
	 * Gets the current {@code TableComponent} data model that is rendered in
	 * the {@link #tableViewer}.
	 * 
	 * @return The current {@code TableComponent}.
	 */
	public TableComponent getTableComponent() {
		return tableComponent;
	}

	/**
	 * Creates a new {@code TableViewer} to show a {@link TableComponent}.
	 * 
	 * @param parent
	 *            The containing {@code Composite}.
	 * @return The created JFace {@code TableViewer}.
	 */
	public static TableViewer createTableViewer(Composite parent) {
		// Create the TableViewer and the underlying Table Control.
		final TableViewer viewer = new TableViewer(parent, SWT.BORDER
				| SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		// Set some properties for the table.
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// Set up the content provider for the viewer. Now the viewer's input
		// can be set.
		viewer.setContentProvider(new TableComponentContentProvider());

		// Enable tool tips for the Table's cells.
		ColumnViewerToolTipSupport.enableFor(viewer, ToolTip.NO_RECREATE);

		// Use a custom comparer to just check references rather than calling
		// equals(Object), which causes strange behavior when multiple rows have
		// the same values.
		viewer.setComparer(new IElementComparer() {
			@Override
			public int hashCode(Object element) {
				return element.hashCode();
			}

			@Override
			public boolean equals(Object a, Object b) {
				return a == b;
			}
		});

		return viewer;
	}

	/**
	 * Creates the JFace {@code Action}s used to add or remove rows to or from
	 * the {@link #tableComponent}.
	 */
	private void createActions() {

		// Create the add action. It will add a new, default row.
		addRowAction = new Action("+", Action.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				tableComponent.addRow();
			}
		};
		addRowAction.setToolTipText("Adds a new, default row.");

		// Create the remove action. It will remove all rows selected in the
		// TableViewer from the TableComponent.
		removeRowAction = new Action("-", Action.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				// The action should only be run once.
				setEnabled(false);
				// Delete all selected rows from the TableComponent.
				for (int i = selectedIndices.size() - 1; i >= 0; i--) {
					tableComponent.deleteRow(selectedIndices.get(i));
				}
				selectedIndices.clear();
				// Make sure the model knows there is no selection.
				tableComponent.setSelectedRows(new ArrayList<Integer>());
			}
		};
		removeRowAction.setToolTipText("Removes all selected rows.");
		removeRowAction.setEnabled(false);

		return;
	}

	/**
	 * Adds listeners to hook the {@link #tableViewer}'s selection to
	 * {@link #selectedIndices}. This enables us to remove selected rows from
	 * the {@link #tableComponent}.
	 */
	private void createTableViewerListeners() {
		// Add a selection listener to tell the TableComponent which rows are
		// selected.
		ISelectionChangedListener listener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				// To delete rows (later), the TableComponent expects indices.
				// Thus, we must keep track of selected indicies by updating the
				// class' list.

				// To select rows, the TableComponent expects an
				// ArrayList<Integer> containing the row IDs, not indices. Thus,
				// we must convert the int[] of indices into an
				// ArrayList<Integer> of IDs to select.
				int[] selected = tableViewer.getTable().getSelectionIndices();
				List<Integer> rowIds = tableComponent.getRowIds();
				ArrayList<Integer> rows = new ArrayList<Integer>(
						selected.length);
				selectedIndices.clear();
				for (int i : selected) {
					selectedIndices.add(i);
					rows.add(rowIds.get(i));
				}
				tableComponent.setSelectedRows(rows);

				// Enable/disable the remove action if the selection is
				// non/empty.
				removeRowAction.setEnabled(!event.getSelection().isEmpty());

				return;
			}
		};
		tableViewer.addSelectionChangedListener(listener);

		return;
	}
}
