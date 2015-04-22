package org.eclipse.ice.client.widgets.jface;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jface.viewers.TextCellEditor;
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

public class FileCellEditor extends TextCellEditor {

	// FIXME The Text field disappears when activated...
	
	private static final int defaultTextStyle = SWT.SINGLE;
	private static final int defaultButtonStyle = SWT.PUSH;

	private final int buttonStyle;

	private Control text;
	private Button button;

	private final AtomicInteger mouseOverCount = new AtomicInteger();
	private MouseTrackListener mouseListener;

	public FileCellEditor(Composite parent) {
		this(parent, defaultTextStyle);
	}

	public FileCellEditor(Composite parent, int textStyle) {
		this(parent, textStyle, defaultButtonStyle);
	}

	public FileCellEditor(Composite parent, int textStyle, int buttonStyle) {
		super(parent, textStyle);
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

		// Create the default text control.
		text = super.createControl(parent);

		// Create the browse Button.
		button = new Button(container, buttonStyle);
		button.setText("...");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("...");
			}
		});

		// Set the layout. It needs to be a GridLayout, where the Text grabs
		// all available horizontal space.
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		container.setLayout(gridLayout);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

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
		text.addMouseTrackListener(mouseListener);
		button.addMouseTrackListener(mouseListener);
	}

	@Override
	public void deactivate() {
		if (text != null && !text.isDisposed()) {
			text.removeMouseTrackListener(getMouseListener());
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
