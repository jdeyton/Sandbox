package org.eclipse.ice.client.widgets.jface;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ComboFileCellEditor extends ComboBoxViewerCellEditor {

	private static final int defaultComboStyle = SWT.DROP_DOWN | SWT.READ_ONLY;
	private static final int defaultButtonStyle = SWT.PUSH;

	private final int buttonStyle;

	private Control combo;
	private Button button;

	private final AtomicInteger mouseOverCount = new AtomicInteger();
	private MouseTrackListener mouseListener;

	public ComboFileCellEditor(Composite parent) {
		this(parent, defaultComboStyle);
	}

	public ComboFileCellEditor(Composite parent, int comboStyle) {
		this(parent, comboStyle, defaultButtonStyle);
	}

	public ComboFileCellEditor(Composite parent, int comboStyle, int buttonStyle) {
		super(parent, comboStyle);
		this.buttonStyle = buttonStyle;
	}

	protected Control createControl(Composite parent) {

		// Create the container. It will hold a Combo widget on the left with
		// the allowed file values and a browse Button on the right to open a
		// browse dialog.
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackground(parent.getBackground());
		container.setForeground(parent.getForeground());
		container.setFont(parent.getFont());

		// Create the default Combo control.
		combo = super.createControl(container);

		// Create the browse Button.
		button = new Button(container, buttonStyle);
		button.setText("...");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("...");
			}
		});

		// Set the layout. It needs to be a GridLayout, where the Combo grabs
		// all available horizontal space.
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		container.setLayout(gridLayout);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

		// Add mouse listeners to the widgets to prevent losing focus too soon.
		MouseTrackListener mouseListener = getMouseListener();
		combo.addMouseTrackListener(mouseListener);
		button.addMouseTrackListener(mouseListener);

		// Add a key listener to cancel if the escape button is pressed while
		// the button has focus.
		button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.character == '\u001b') { // Escape
					fireCancelEditor();
				}
			}
		});

		return container;
	}

	@Override
	protected void focusLost() {
		// Only lose focus if the mouse isn't over one of the buttons!
		if (mouseOverCount.get() <= 0) {
			mouseOverCount.set(0);
			super.focusLost();
		}
	}

	@Override
	protected void doSetFocus() {
		super.doSetFocus();

		MouseTrackListener mouseListener = getMouseListener();
		combo.addMouseTrackListener(mouseListener);
		button.addMouseTrackListener(mouseListener);
	}

	@Override
	public void deactivate() {
		if (combo != null && !combo.isDisposed()) {
			combo.removeMouseTrackListener(getMouseListener());
		}
		if (button != null && !button.isDisposed()) {
			button.removeMouseTrackListener(getMouseListener());
		}
		super.deactivate();
	}

	private MouseTrackListener getMouseListener() {
		if (mouseListener == null) {
			mouseListener = new MouseTrackAdapter() {
				@Override
				public void mouseEnter(MouseEvent e) {
					mouseOverCount.incrementAndGet();
				}

				@Override
				public void mouseExit(MouseEvent e) {
					mouseOverCount.decrementAndGet();
				}
			};
		}
		return mouseListener;
	}
}
