/**
 * 
 */
package com.bar.foo.javafx.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.bar.foo.javafx.Node;

/**
 * @author Jordan Deyton
 *
 */
public class MultiViewLauncher {

	public static int count = 0;
	public static Node stageRoot;

	public static void main(String[] args) {

		// Create an SWT shell for testing purposes.
		TestLauncher launcher = new TestLauncher();
		Shell shell = launcher.getShell();
		shell.setText("JavaFX Multi-View Test");

		SashForm sashForm = new SashForm(shell, SWT.HORIZONTAL);
		Composite leftComposite = new Composite(sashForm, SWT.NONE);
		final Composite rightComposite = new Composite(sashForm, SWT.NONE);

		leftComposite.setLayout(new GridLayout());
		// Create a Text field for setting the name for a new view.
		final Text text = new Text(leftComposite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// A map that contains all views.
		final Map<String, EmbeddedView> viewMap = new HashMap<String, EmbeddedView>();

		// Create a List widget for selecting a color.
		final List colorList = new List(leftComposite, SWT.BORDER);
		colorList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		// Create a HashMap to contain the available colors.
		final Map<String, Color> colors = new HashMap<String, Color>();
		colors.put("Blue", Color.BLUE);
		colors.put("Red", Color.RED);
		colors.put("Green", Color.GREEN);
		colors.put("White", Color.WHITE);
		colors.put("Yellow", Color.YELLOW);
		colors.put("Magenta", Color.MAGENTA);
		colors.put("Cyan", Color.CYAN);
		colors.put("Orange", Color.ORANGE);
		colors.put("Pink", Color.PINK);
		colors.put("Gray", Color.GRAY);
		colors.put("Brown", Color.BROWN);
		colors.put("Random", null);
		// Add the key strings to the List.
		for (String color : colors.keySet())
			colorList.add(color);
		colorList.select(2);

		// Create a Composite with an add and delete button.
		Composite buttonComposite = new Composite(leftComposite, SWT.BORDER);
		buttonComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false));
		GridLayout gridLayout = new GridLayout(2, true);
		gridLayout.marginLeft = 10;
		gridLayout.marginRight = 10;
		gridLayout.horizontalSpacing = 10;
		buttonComposite.setLayout(gridLayout);
		// Create the add button.
		Button add = new Button(buttonComposite, SWT.PUSH);
		add.setText("Add");
		// Create the delete button.
		Button delete = new Button(buttonComposite, SWT.PUSH);
		delete.setText("Delete");

		// Create a List widget to contain the current views.
		final List viewList = new List(leftComposite, SWT.BORDER);
		viewList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		rightComposite.setLayout(new GridLayout());

		// Create a view of the entire scene.
		EmbeddedView stageView = new EmbeddedView(null, Color.SILVER);
		Composite stageComposite = stageView.createComposite(rightComposite);
		stageComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		stageRoot = stageView.getRoot();
		stageRoot.setRx(180.0);
		rightComposite.layout();
		
		// Add the button listeners for the Add and Delete buttons.
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Get the name and color.
				String name = text.getText().trim();
				String[] selection = colorList.getSelection();

				Color color = null;
				if (selection.length > 0) {
					color = colors.get(selection[0]);
					if (color == null) {
						Random random = new Random();
						int red = random.nextInt(256);
						int green = random.nextInt(256);
						int blue = random.nextInt(256);
						color = Color.rgb(red, green, blue);
					}
				}

				if (!name.isEmpty() && color != null
						&& !viewMap.containsKey(name)) {
					count++;
					// Update the layout with the new number of columns.
					int columns = (int) Math.ceil(Math.sqrt(count));
					rightComposite.setLayout(new GridLayout(columns, true));

					// Create the view.
					EmbeddedView view = new EmbeddedView(stageRoot, color);
					Composite viewComposite = view
							.createComposite(rightComposite);
					viewComposite.setLayoutData(new GridData(SWT.FILL,
							SWT.FILL, true, true));

					// Bookkeeping.
					viewList.add(name);
					viewMap.put(name, view);

					// Lay out the right Composite.
					rightComposite.layout();
				}
			}
		});
		delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Get the index and name from the list.
				int index = viewList.getSelectionIndex();
				if (index < 0)
					return;
				String name = viewList.getItem(index);
				EmbeddedView view = viewMap.get(name);

				if (view != null) {
					count--;
					// Update the layout with the new number of columns.
					int columns = (int) Math.ceil(Math.sqrt(count));
					rightComposite.setLayout(new GridLayout(columns, true));

					// Delete the view.
					view.dispose();
					// Bookkeeping.
					viewList.remove(index);
					viewMap.remove(name);

					// Lay out the right Composite.
					rightComposite.layout();
				}
			}
		});

		// Open and run the shell on the main thread.
		launcher.openShell();

		// Close any shell-dependent resources here.
		// The underlying canvases should already be disposed, but we should
		// dispose any other resources managed by the EmbeddedViews.
		for (EmbeddedView view : viewMap.values()) {
			view.dispose();
		}
		stageView.dispose();
		
		return;
	}

}
