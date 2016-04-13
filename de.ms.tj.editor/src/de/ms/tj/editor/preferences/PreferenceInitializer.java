package de.ms.tj.editor.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;

import de.ms.tj.editor.internal.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences node = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		node.putInt(ITjPreferences.SH_CODE_COLOR, 0x000000);
		node.putInt(ITjPreferences.SH_COMMENT_COLOR, 0x808080);
		node.putInt(ITjPreferences.SH_STRING_COLOR, 0x00A000);
	}

}
