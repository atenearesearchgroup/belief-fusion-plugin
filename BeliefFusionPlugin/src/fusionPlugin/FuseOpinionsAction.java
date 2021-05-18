/*
 * Copyright (c) 2002 NoMagic, Inc. All Rights Reserved.
 */
package fusionPlugin;

import com.jidesoft.chart.preference.LineWidthChooser;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.teamwork.application.ProfilesHelper;
import com.nomagic.magicdraw.ui.actions.DefaultDiagramAction;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import com.nomagic.ui.ScalableImageIcon;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceSpecification;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceValue;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralString;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Slot;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.impl.InstanceSpecificationImpl;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.impl.LiteralStringImpl;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import com.nomagic.uml2.ext.magicdraw.metadata.UMLPackage;

import uDataTypes.SBoolean;

import javax.swing.*;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

/**
 * Action shows message: how many symbols are selected in diagram.
 *
 * @author Donatas Simkunas
 */
public class FuseOpinionsAction extends DefaultDiagramAction
{
	private String windowMessage;
	
	/**
	 * Creates diagram action with name "Diagram Example Action".
	 */
	public FuseOpinionsAction()
	{
		super("Fuse available opinions", "Fuse available opinions", KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.SHIFT_MASK + KeyEvent.ALT_MASK), null);
		setDescription("Fuse the opinions available in the active diagram");
		setSmallIcon(new ScalableImageIcon(getClass(), "logoFusion.png"));
	
		windowMessage = new String();
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * Displaying how many symbols are selected  in diagram.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		fuseOpinions();
	}

	/**
	 * @see com.nomagic.actions.NMAction#updateState()
	 * Enabling action only when there are selected symbols in diagram.
	 */
	@Override
	public void updateState()
	{
		//setEnabled(getSelected().size() > 0);
	}
	
	private void fuseOpinions() {
		Project project = Application.getInstance().getProject();

		String profileName = "BeliefUncertaintyProfile";
				//"UncertaintyDSML";
		String stereotypeName = 
				"UncertainElementFusion";
				//"UncertainElement_wF";
				//"UncertainElement";
		
		Stereotype uncertainElementStereotype = getStereotype(project, profileName, stereotypeName);
		List<Element> stereotypedElements = getStereotypedElements(project, uncertainElementStereotype);
		
		Application.getInstance().getGUILog().showMessage("Fusion process completed.");
		System.out.println("---------------------");
		
		for(Element e : stereotypedElements) {
			List<Belief> beliefs = getBeliefs(e);
			List<SBoolean> fusionValues = new ArrayList<SBoolean>(); 
			for (Belief b : beliefs) {
				fusionValues.add(b.getOpinion());
			}
			
			if(fusionValues.size()>1 && differentAgents(beliefs)) {
				System.out.println("FUSION RESULTS");
				SBoolean BCF = SBoolean.beliefConstraintFusion(fusionValues);
				if(BCF != null) {
					System.out.println("Belief Constraint Fusion: " + BCF.toString());
					setStereotypeValue(project, e, uncertainElementStereotype, "beliefConstraintFusion", BCF.toString());
				} else {
					windowMessage += composeMessage(e, "Belief Constraint Fusion: Cannot fuse totally conflicting opinions");
					System.out.println("Belief Constraint Fusion: Cannot fuse totally conflicting opinions");
					setStereotypeValue(project, e, uncertainElementStereotype, "beliefConstraintFusion", "Undefined");
				}
				
				SBoolean ABF = SBoolean.averageBeliefFusion(fusionValues);
				System.out.println("Average Belief Fusion: " + ABF.toString());
				setStereotypeValue(project, e, uncertainElementStereotype, "averagingBF", ABF.toString());
				
				SBoolean ACBF = SBoolean.cumulativeBeliefFusion(fusionValues);
				System.out.println("Aleatory Cumulative Belief Fusion: " + ACBF.toString());
				setStereotypeValue(project, e, uncertainElementStereotype, "aleatoryCumulativeBF", ACBF.toString());
				
				SBoolean ECBF = SBoolean.epistemicCumulativeBeliefFusion(fusionValues);
				System.out.println("Epistemic Cumulative Belief Fusion: " + ECBF.toString());
				setStereotypeValue(project, e, uncertainElementStereotype, "epistemicCumulativeBF", ECBF.toString());
				
				SBoolean WBF = SBoolean.weightedBeliefFusion(fusionValues);
				System.out.println("Weighted Belief Fusion: " + WBF.toString());
				setStereotypeValue(project, e, uncertainElementStereotype, "weightedBF", WBF.toString());
				
				if(sameBaseRates(fusionValues)) {
					SBoolean CC = SBoolean.consensusAndCompromiseFusion(fusionValues);
					System.out.println("Consensus and Compromise Fusion: " + CC.toString());
					setStereotypeValue(project, e, uncertainElementStereotype, "consensusCompromiseBF", CC.toString());
				} else {
					windowMessage+= composeMessage(e , "Consensus and Compromise Fusion: Base rates for CC Fusion must be the same");
					System.out.println("Consensus and Compromise Fusion: Base rates for CC Fusion must be the same" );
					setStereotypeValue(project, e, uncertainElementStereotype, "consensusCompromiseBF", "Undefined");
				}
			} else if(fusionValues.size()>1) {
				windowMessage += composeMessage(e, "Opinions must be from different agents to perform the fusion process.");
			}
		}
		if(!windowMessage.isEmpty()) {
			Application.getInstance().getGUILog().showMessage(windowMessage);
		}
	}
	
	private boolean differentAgents(List<Belief> beliefs) {
		boolean res = true;
		for(int i = 0; i<beliefs.size(); i++) {
			for(int j = 0; j<beliefs.size(); j++) {
				if(beliefs.get(i).getAgent().equals(beliefs.get(j).getAgent()) && j != i) {
					res = false;
					break;
				}
			}
		}
		return res;
	}
	
	private String composeMessage(Element e, String message) {
		if(e.getHumanName().equals("Slot")) {
			return ("[WARNING: " + e.getHumanName() + " in " + e.getObjectParent().getHumanName().split(" ")[2] + "] " + message + "\n");
		} else {
			return ("[WARNING: " + e.getHumanName() + "] " + message + "\n");
		}
	}
	
	private boolean sameBaseRates(List<SBoolean> beliefs) {
		SBoolean first = beliefs.get(0);
		boolean result = true;
		for(SBoolean b : beliefs) {
			result = result && b.baseRate() == first.baseRate();
			if(!result) {
				break;
			}
		}
		return result;
	}

	
	private List<Element> getStereotypedElements(Project project, Stereotype stereotype) {
		// Find all elements in the active diagram which have an stereotype applied
		List<Element> stereotypedElements = StereotypesHelper.getExtendedElements(stereotype);
		stereotypedElements.retainAll(project.getActiveDiagram().getUsedModelElements());
		
		return stereotypedElements;
	}

	private Stereotype getStereotype(Project project, String profile, String stereotype) {
		Collection<Stereotype> allAppliedStereotypes = StereotypesHelper.getAllStereotypes(project);

		// Find the stereotype UncertainElement which belongs to the profile UncertaintyDSML
		Stereotype uncertainElementStereotype = null;
		for(Stereotype s : allAppliedStereotypes) {
			if(s.getProfile().getName().equals(profile) && s.getName().equals(stereotype)) {
				uncertainElementStereotype = s;
				break;
			}
		}
		return uncertainElementStereotype;
	}
	
	private void setStereotypeValue(Project project, Element e, Stereotype s, String tagName, String value) {
		final Slot slot = StereotypesHelper.getSlot(e, s, tagName, true, false);
		LiteralString literalString = project.getElementsFactory().createLiteralStringInstance();
		literalString.setValue(value);
		if(!slot.getValue().isEmpty()) slot.getValue().remove(0);
		slot.getValue().add(literalString);
	}
	
	private List<Belief> getBeliefs(Element e){
		List<Belief> results = new ArrayList<Belief>();
		System.out.println("Stereotyped element: " + e.getHumanName());
		// To get the tag value
		List<InstanceSpecification> tagValues = StereotypesHelper.getStereotypePropertyValue(e, "UncertainElement", "beliefs");
		for(InstanceSpecification tagValue : tagValues) {
			TreeIterator<EObject> beliefs = tagValue.eAllContents();
			LiteralString subjectiveOpinion = null;
			String agent = null;
			// Each belief contains an agent and a SBoolean value
			while(beliefs.hasNext()) {
				EObject belief = beliefs.next();
				List<EObject> fin = belief.eContents();
				for(EObject o2 : fin) {
					if(o2 instanceof LiteralString) {
						subjectiveOpinion = (LiteralString) o2;
						System.out.println("Subjective opinion: " + subjectiveOpinion.getValue());
					} else if(o2 instanceof InstanceValue){
						agent = ModelHelper.getValueString((InstanceValue)o2);		
						System.out.println("Agent: " + agent);
					} else {
						System.out.println("Unexpected value");
					}
				}
				
			}
			if(agent != null && subjectiveOpinion != null) {
				try {
					Belief b = new Belief(agent, subjectiveOpinion);
					results.add(b);
				} catch (InputMismatchException error) {
					windowMessage += "[ERROR] " + error.getMessage() + "\n";
				}
			}
		}
		return results;		
	}
}
