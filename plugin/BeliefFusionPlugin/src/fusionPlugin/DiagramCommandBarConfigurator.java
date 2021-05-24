/*
 * Copyright (c) 2014 NoMagic, Inc. All Rights Reserved.
 */
package fusionPlugin;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.ActionsManager;
import com.nomagic.magicdraw.ui.actions.DefaultDiagramAction;

/**
 * @author Martynas Lelevicius
 */
public class DiagramCommandBarConfigurator implements AMConfigurator
{
	private final DefaultDiagramAction action;

	public DiagramCommandBarConfigurator(DefaultDiagramAction action)
	{
		this.action = action;
	}

	@Override
	public void configure(ActionsManager mngr)
	{
		final ActionsCategory category = new ActionsCategory();
		mngr.addCategory(category);
		category.addAction(action);
	}

	@Override
	public int getPriority()
	{
		return AMConfigurator.MEDIUM_PRIORITY;
	}
}
