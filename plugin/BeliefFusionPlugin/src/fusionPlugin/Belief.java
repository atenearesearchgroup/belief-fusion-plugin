package myplugin2;

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
	
//	private enum ULiterals {CERTAIN, PROBABLE, POSSIBLE, UNCERTAIN, IMPROBABLE, UNLIKELY, IMPOSSIBLE };
	private final String SBOOLEAN_LITERAL_REGEX = "SBoolean(\\s)*\\([a-zA-Z]*((\\s)*,(\\s)*){1}((([0-9]+)(\\.[0-9]+)?)){1}(\\s)*\\)";
			
	private final String DEGREE_OF_CERTAINTY_CERTAIN = "CERTAIN"; 		 
	private final String DEGREE_OF_CERTAINTY_PROBABLE = "PROBABLE";
	private final String DEGREE_OF_CERTAINTY_POSSIBLE = "POSSIBLE";		 
	private final String DEGREE_OF_CERTAINTY_UNCERTAIN = "UNCERTAIN";
	private final String DEGREE_OF_CERTAINTY_IMPROBABLE = "IMPROBABLE";
	private final String DEGREE_OF_CERTAINTY_UNLIKELY = "UNLIKELY";
	private final String DEGREE_OF_CERTAINTY_IMPOSSIBLE = "IMPOSSIBLE";
	
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
		
		Pattern sbooleanliteralPattern = Pattern.compile(SBOOLEAN_LITERAL_REGEX);
		Matcher sbooleanliteralMatcher = sbooleanliteralPattern.matcher(opinion);
		
		Pattern booleanPattern = Pattern.compile(BOOLEAN_REGEX);
		Matcher booleanMatcher = booleanPattern.matcher(opinion);
		
		SBoolean result;
//		System.out.println("Opinion" + opinion);
//		System.out.println(opinion.equals(DEGREE_OF_CERTAINTY_PROBABLE));
		
	
		if (sbooleanliteralMatcher.matches()) {
			result = parseSBooleanLiteral(opinion);
		} else if (sbooleanMatcher.matches()) {
			result =  parseSBoolean(opinion);
		} else if (ubooleanMatcher.matches()) {
			result =  parseUBoolean(opinion);
		} else if (booleanMatcher.matches()) {
			result =  new SBoolean(Boolean.parseBoolean(opinion));
		} else {
			throw new InputMismatchException("Input string " + opinion + " does not match a valid Belief value.");
		}
		System.out.println("Parsing result =" + result);
		return result;
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
	
	private SBoolean parseSBooleanLiteral(String opinion) {
		String[] tokens = opinion.split("SBoolean\\(|,|\\)");


		System.out.println("TOKENS" + tokens);
		String literal = tokens[1];
		double baseRate = Double.parseDouble(tokens[2]);
				
		SBoolean result; 
		
		if (literal.equals(DEGREE_OF_CERTAINTY_CERTAIN)) {
			result =  new SBoolean(1, 0, 0, baseRate);
		} else if (literal.equals(DEGREE_OF_CERTAINTY_PROBABLE)) {
			result =  new SBoolean(0.67, 0, 0.33, baseRate);
		} else if (literal.equals(DEGREE_OF_CERTAINTY_POSSIBLE)){
			result =  new SBoolean(0.33, 0, 0.67, baseRate);
		} else if (literal.equals(DEGREE_OF_CERTAINTY_UNCERTAIN)){
			result =  new SBoolean(0, 0, 1, baseRate);
		} else if (literal.equals(DEGREE_OF_CERTAINTY_IMPROBABLE)){
			result =  new SBoolean(0, 0.33, 0.67, baseRate);
		} else if (literal.equals(DEGREE_OF_CERTAINTY_UNLIKELY)){
			result =  new SBoolean(0, 0.67, 0.33, baseRate);
		} else if (literal.equals(DEGREE_OF_CERTAINTY_IMPOSSIBLE)){
			result =  new SBoolean(0, 1, 0, baseRate);
		} else {
			throw new InputMismatchException("Input string " + opinion + " does not match a valid Belief value.");
		}
		
		return result;
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
