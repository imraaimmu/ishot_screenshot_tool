package tool.ishot;

import javax.swing.JComponent;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class RequestFocusListener implements AncestorListener {

	public RequestFocusListener() {
		this(true);
	}

	public RequestFocusListener(boolean removeListener) {
		this.removeListener = removeListener;
	}

	private boolean removeListener;

	public void ancestorAdded(AncestorEvent event) {
		JComponent component = event.getComponent();
		component.requestFocusInWindow();
		if(removeListener){
			component.removeAncestorListener(this);
		}

	}

	public void ancestorRemoved(AncestorEvent event) {

	}

	public void ancestorMoved(AncestorEvent event) {

	}

}
