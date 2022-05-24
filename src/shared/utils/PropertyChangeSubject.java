package shared.utils;

import java.beans.PropertyChangeListener;

public interface PropertyChangeSubject {

	void addListener(PropertyChangeListener listener);
	
	void removeListener(PropertyChangeListener listener);

	void addListener(String eventName, PropertyChangeListener listener);

	void removeListener(String eventName, PropertyChangeListener listener);

}