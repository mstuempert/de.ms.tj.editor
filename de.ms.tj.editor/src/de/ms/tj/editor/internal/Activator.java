package de.ms.tj.editor.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.ms.tj.editor.preferences.IPreferenceManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.ms.tj.editor"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private PreferenceManager pManager;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}
	
	public IPreferenceManager getPreferenceManager() {
		if (this.pManager == null) {
			this.pManager = new PreferenceManager();
		}
		return this.pManager;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	public static final void logException(Exception e) {
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
	}
	
	public static final void logStatus(IStatus status) {
		getDefault().getLog().log(status);
	}

}
