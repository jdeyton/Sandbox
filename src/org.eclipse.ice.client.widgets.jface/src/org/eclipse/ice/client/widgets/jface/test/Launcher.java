package org.eclipse.ice.client.widgets.jface.test;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ice.client.widgets.jface.table.TableViewComposite;
import org.eclipse.ice.datastructures.form.AllowedValueType;
import org.eclipse.ice.datastructures.form.BasicEntryContentProvider;
import org.eclipse.ice.datastructures.form.Entry;
import org.eclipse.ice.datastructures.form.TableComponent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Launcher {
	public static void main(String[] args) {

		// Create the Display.
		final Display display = new Display();

		// Create the Shell (window).
		final Shell shell = new Shell(display);
		shell.setText("VisIt Tester");
		shell.setSize(1024, 768);
		shell.setLayout(new GridLayout(1, false));

		TableViewComposite tableComposite = new TableViewComposite(shell,
				SWT.NONE);
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));

		TableComponent table = createTableComponent();
		tableComposite.setTableComponent(table);

		// Open the shell.
		shell.open();

		// SOP UI loop.
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

		return;
	}

	private static TableComponent createTableComponent() {
		TableComponent table = new TableComponent();
		table.setName("Table");
		table.setDescription("Table Description");
		
		Entry column;
		BasicEntryContentProvider contentProvider;
		List<String> allowedValues;
		
		List<Entry> template = new ArrayList<Entry>();
		
		contentProvider = new BasicEntryContentProvider();
		contentProvider.setDefaultValue("default");
		column = new Entry(contentProvider);
		column.setName("Basic Text Entry");
		template.add(column);
		
		contentProvider = new BasicEntryContentProvider();
		contentProvider.setDefaultValue("One");
		contentProvider.setAllowedValueType(AllowedValueType.Discrete);
		allowedValues = new ArrayList<String>();
		allowedValues.add("One");
		allowedValues.add("Two");
		allowedValues.add("Three");
		contentProvider.setAllowedValues((ArrayList<String>) allowedValues);
		column = new Entry(contentProvider);
		column.setName("Discrete Entry");
		template.add(column);
		
		contentProvider = new BasicEntryContentProvider();
		contentProvider.setDefaultValue("No File");
		contentProvider.setAllowedValueType(AllowedValueType.File);
		column = new Entry(contentProvider);
		column.setName("File Entry");
		template.add(column);
		
		contentProvider = new BasicEntryContentProvider();
		contentProvider.setDefaultValue("No File");
		contentProvider.setAllowedValueType(AllowedValueType.File);
		allowedValues = new ArrayList<String>();
		allowedValues.add("No File");
		allowedValues.add("File One");
		allowedValues.add("File Two");
		contentProvider.setAllowedValues((ArrayList<String>) allowedValues);
		column = new Entry(contentProvider);
		column.setName("Combo File Entry");
		template.add(column);
		
		table.setRowTemplate((ArrayList<Entry>) template);
		
		return table;
	}

}
