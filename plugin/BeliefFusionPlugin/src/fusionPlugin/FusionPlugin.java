package fusionPlugin;

import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;
import com.nomagic.magicdraw.actions.ActionsID;
import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.plugins.Plugin;
import com.nomagic.magicdraw.ui.actions.DefaultDiagramAction;
import com.nomagic.magicdraw.ui.browser.actions.DefaultBrowserAction;
import com.nomagic.magicdraw.uml.DiagramType;

public class FusionPlugin extends Plugin
{
	@Override
	public void init()
	{
		final ActionsConfiguratorsManager manager = ActionsConfiguratorsManager.getInstance();
		
		// adding action to object diagram
		final DefaultDiagramAction objectDiagramAction = new FuseOpinionsAction();
		final DiagramConfigurator objectDiagramConfigurator = new DiagramConfigurator(objectDiagramAction);
		manager.addDiagramContextConfigurator(DiagramType.UML_OBJECT_DIAGRAM, objectDiagramConfigurator);
		manager.addDiagramShortcutsConfigurator(DiagramType.UML_OBJECT_DIAGRAM, objectDiagramConfigurator);
		manager.addDiagramToolbarConfigurator(DiagramType.UML_OBJECT_DIAGRAM, objectDiagramConfigurator);
		manager.addDiagramCommandBarConfigurator(DiagramType.UML_OBJECT_DIAGRAM, new DiagramCommandBarConfigurator(objectDiagramAction));
	}

	@Override
	public boolean close()
	{	
		return true;
	}

	@Override
	public boolean isSupported()
	{
		return true;
	}
}
