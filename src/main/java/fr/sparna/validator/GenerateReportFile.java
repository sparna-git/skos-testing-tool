package fr.sparna.validator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;



import at.ac.univie.mminf.qskos4j.issues.Issue;
import at.ac.univie.mminf.qskos4j.result.Result;
import at.ac.univie.mminf.qskos4j.util.IssueDescriptor.IssueType;

public class GenerateReportFile {
	
	protected Collection<Issue> issues;
	
	protected String lang;
	
	protected String filename;
	
	  public GenerateReportFile(Collection<Issue> issues, String lang,String filename) {
		super();
		this.issues = issues;
		this.lang = lang;
		this.filename=filename;
	}

	private String createReportSummary() throws IOException {
	        StringBuffer summary = new StringBuffer();
	        String issuedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	        summary.append("********"+filename+" "+issuedDate+"*************\n\n\n");
	        
	        for (Issue issue : issues) {
	        	// skip statistical issues
	        	if(issue.getIssueDescriptor().getType() == IssueType.ANALYTICAL) {
		        	IssueDescription desc = ValidatorConfig.getInstance().getApplicationData().findIssueDescriptionById(issue.getIssueDescriptor().getId());
		            summary.append(desc.getLabelByLang(lang) + ": " + prepareOccurrenceText(issue) + "\n");
	        	}
	        }

	        summary.append("\n");
	        return summary.toString();
	    }

	    private String prepareOccurrenceText(Issue issue) {
	        String occurrenceText = "";
	        if (issue.getResult().isProblematic()) {
	            occurrenceText = "FAIL";
	            try {
	                String occurrenceCount = Long.toString(issue.getResult().occurrenceCount());
	                occurrenceText += " (" +occurrenceCount+ ")";
	            }
	            catch (UnsupportedOperationException e) {
	                // ignore this
	            }
	        }
	        else {
	            occurrenceText = "OK";
	        }

	        return occurrenceText;
	    }

	    private void writeReportBody(BufferedWriter reportWriter)
	        throws IOException
	    {
	        reportWriter.write("* Detailed coverage of each Quality Issue:\n\n");
	        Iterator<Issue> issueIt = issues.iterator();
	        while (issueIt.hasNext()) {
	            Issue issue = issueIt.next();

	            writeTextReport(issue, reportWriter);

	            if (issueIt.hasNext()) {
	                reportWriter.newLine();
	            }

	           
	        }
	    }
	    
	    private void writeTextReport(Issue issue, BufferedWriter writer)
	            throws IOException
	        {
		    	writer.write(createIssueHeader(issue));
		        writer.newLine();
	            issue.getResult().generateReport(writer, Result.ReportFormat.TXT, Result.ReportStyle.SHORT);

	            writer.newLine();
	            issue.getResult().generateReport(writer, Result.ReportFormat.TXT, Result.ReportStyle.EXTENSIVE);

	            writer.newLine();
	            writer.flush();
	        }
	   
	   public void outputIssuesReport(OutputStream stream)
	   throws IOException {
	            BufferedWriter reportWriter = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"));
	            
	            String reportSummary = createReportSummary();
	            reportWriter.write(reportSummary);
	            writeReportBody(reportWriter);

	            reportWriter.close();
	        }
	   
	   private String createIssueHeader(Issue issue) {
		  
		   IssueDescription desc = ValidatorConfig.getInstance().getApplicationData().findIssueDescriptionById(issue.getIssueDescriptor().getId());
		   
	        String header = "--- " +desc.getLabelByLang(lang);
	        URL weblink = issue.getIssueDescriptor().getWeblink();
	        header += "\nDescription: " +desc.getDescriptionByLang(lang);

	        if (weblink != null) {
	            header += "\nDetailed information: " +weblink.toString();
	        }
	        return header;
	    }
}
