/*
 * Copyright (C) 2006, 2007 Clam <clamisgood@gmail.com>
 * 
 * This file is part of LateralGM.
 * LateralGM is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for details.
 */

package org.lateralgm.resources.library;

import java.util.ArrayList;

public class RLibrary
	{
	public int id = 0;
	public String tabCaption = "";
	public boolean advanced = false;
	public ArrayList<RLibAction> libActions = new ArrayList<RLibAction>();

	public RLibAction addLibAction()
		{
		RLibAction act = new RLibAction();
		libActions.add(act);
		return act;
		}

	public RLibAction getLibAction(int id)
		{
		for (RLibAction act : libActions)
			if (act.id == id) return act;
		return null;
		}
	}
