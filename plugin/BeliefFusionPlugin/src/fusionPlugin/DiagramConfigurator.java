/*
 * Copyright (c) 2002 NoMagic, Inc. All Rights Reserved.
 */
package fusionPlugin;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.ActionsManager;
import com.nomagic.magicdraw.actions.ActionsID;
import com.nomagic.magicdraw.actions.DiagramContextAMConfigurator;
import com.nomagic.magicdraw.actions.MDActionsCategory;
import com.nomagic.magicdraw.ui.actions.DefaultDiagramAction;
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;

/**
 * Class for configuring diagram context menu actions, diagram shortcuts and diagram toolbar.
 *
 * @author Donatas Simkunas
 */
public class DiagramConfigurator implements DiagramContextAMConfigurator, AMConfigurator
{
	/**
	 * Action will be added to diagram.
	 */
	private final DefaultDiagramAction action;

	/**
	 * Creates configurator which adds given action.
	 *
	 * @param action action to be added to manager.
	 */
	public DiagramConfigurator(DefaultDiagramAction action)
	{
		this.action = action;
	}

	@Override
	public void configure(ActionsManager manager, DiagramPresentationElement diagram, PresentationElement[] selected, PresentationElement requestor)
	{
		final ActionsCategory category = new MDActionsCategory();
		category.addAction(action);
		manager.addCategory(category);
	}

	/**
	 * @see com.nomagic.actions.AMConfigurator#configure(com.nomagic.actions.ActionsManager)
	 * Configuring toolbar and shortcuts.
	 */
	@Override
	public void configure(ActionsManager manager)
	{
		if (manager.getActionFor(action.getID()) == null)
		{
			ActionsCategory category = (ActionsCategory) manager.getActionFor(ActionsID.CLASS_DIAGRAM_ELEMENTS);
			if (category != null)
			{
				category.addAction(action);
			}
		}
	}

	@Override
	public int getPriority()
	{
		return AMConfigurator.MEDIUM_PRIORITY;
	}

}
