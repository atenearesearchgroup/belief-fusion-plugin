package fusionPlugin;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;

import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.InstanceValue;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.LiteralString;

import uDataTypes.SBoolean;
import uDataTypes.UBoolean;

public class Belief {
	private final String SBOOLEAN_REGEX = "SBoolean(\\s)*\\(((([0-9]+)(\\.[0-9]+)?)(\\s)*,(\\s)*){3}((([0-9]+)(\\.[0-9]+)?)){1}(\\s)*\\)";
	private final String UBOOLEAN_REGEX = "UBoolean(\\s)*\\((\\s)*(true|false)(\\s)*,(\\s)*((([0-9]+)(\\.[0-9]+)?)){1}(\\s)*\\)";
	private final String BOOLEAN_REGEX = "(true|false)";
	
	private String agent;
	private SBoolean opinion;
	
	public Belief(String agent, LiteralString opinion) {
		this.agent = agent;
		this.opinion = parseValue(opinion.getValue().toString());
	}
	
	private SBoolean parseValue(String opinion) throws InputMismatchException {
		Pattern sbooleanPattern = Pattern.compile(SBOOLEAN_REGEX);
		Matcher sbooleanMatcher = sbooleanPattern.matcher(opinion);
		
		Pattern ubooleanPattern = Pattern.compile(UBOOLEAN_REGEX);
		Matcher ubooleanMatcher = ubooleanPattern.matcher(opinion);
		
		Pattern booleanPattern = Pattern.compile(BOOLEAN_REGEX);
		Matcher booleanMatcher = booleanPattern.matcher(opinion);
		
		if(sbooleanMatcher.matches()) {
			return parseSBoolean(opinion);
		} else if (ubooleanMatcher.matches()) {
			return parseUBoolean(opinion);
		} else if (booleanMatcher.matches()) {
			return new SBoolean(Boolean.parseBoolean(opinion));
		} else {
			throw new InputMismatchException("Input string " + opinion + " does not match a valid Belief value.");
		}
	}
	
	private SBoolean parseSBoolean(String opinion) {
		String[] tokens = opinion.split("SBoolean\\(|,|\\)");
		double[] values = new double[4]; 
		int i = 0;
		for(String t : tokens) {
			if(!t.isEmpty()) {
				values[i] = Double.parseDouble(t);
				i++;
			}
		}
		return new SBoolean(values[0], values[1], values[2], values[3]);
	}
	
	private SBoolean parseUBoolean(String opinion) {
		String[] tokens = opinion.split("UBoolean\\(|,|\\)");
		return new SBoolean(new UBoolean(Boolean.parseBoolean(tokens[1]), Double.parseDouble(tokens[2])));
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public SBoolean getOpinion() {
		return opinion;
	}

	public void setOpinion(SBoolean opinion) {
		this.opinion = opinion;
	}
}
