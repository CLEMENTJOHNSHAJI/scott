package hu.advancedweb.scott.runtime.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Model object for the detailed failure report.
 * 
 * @author David Csakvari
 */
class ScottReport {
	
	private TreeMap<Integer, String> sourceForLineNumbers = new TreeMap<>();
	private List<Snapshot> initialSnapshots = new ArrayList<>();
	private Map<Integer, List<Snapshot>> snapshotsForLineNumbers = new TreeMap<>();
	private int beginLineNumber;
	private int exceptionLineNumber;
	private String exceptionMessage;
	private String exceptionClassName;
	
	public void setBeginLine(int beginLine) {
		this.beginLineNumber = beginLine;
	}

	public void addLine(String source) {
		sourceForLineNumbers.put(beginLineNumber, source);
		beginLineNumber++;
	}
	
	public void addInitialSnapshot(String name, String value) {
		initialSnapshots.add(new Snapshot(name, value));
	}
	
	public void addSnapshot(int lineNumber, String name, String value) {
		checkIfSourceFound(lineNumber);
		List<Snapshot> variableSnapshots = snapshotsForLineNumbers.getOrDefault(lineNumber, new ArrayList<Snapshot>());
		variableSnapshots.add(new Snapshot(name, value));
		snapshotsForLineNumbers.put(lineNumber, variableSnapshots);
	}
	
	public void setException(int lineNumber, String exceptionClassName, String exceptionMessage) {
		checkIfSourceFound(lineNumber);
		this.exceptionLineNumber = lineNumber;
		this.exceptionClassName = exceptionClassName;
		this.exceptionMessage = exceptionMessage;
	}
	
	public TreeMap<Integer, String> getSourceLines() {
		return sourceForLineNumbers;
	}
	
	public List<Snapshot> getVariableSnapshots(int lineNumber) {
		return Collections.unmodifiableList(snapshotsForLineNumbers.getOrDefault(lineNumber, new ArrayList<Snapshot>()));
	}
	
	public List<Snapshot> getInitialSnapshots() {
		return Collections.unmodifiableList(initialSnapshots);
	}
	
	public int getBeginLineNumber() {
		return beginLineNumber;
	}
	
	public int getExceptionLineNumber() {
		return exceptionLineNumber;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}
	
	public String getExceptionClassName() {
		return exceptionClassName;
	}
	
	private void checkIfSourceFound(int lineNumber) {
		if (!sourceForLineNumbers.containsKey(lineNumber)) {
			sourceForLineNumbers.put(lineNumber, "???");
		}
	}


}
