/*---------------------------------------------------------------------
 *  File: $Id: HarroldGuptaSoffaSyntheticExperiment.java,v 1.26 2007/07/02 13:36:17 smitha Exp $   
 *  Version:  $Revision: 1.26 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package redupr;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Vector;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream; 
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class HarroldGuptaSoffaSyntheticExperiment
{
	public static void main(String[] args)throws IOException
	{
		final String DIRTODATA = "data/diatoms/reduce/synthetic_test_data/";
		
		ArrayList rowCol = new ArrayList();
		rowCol.add("c");
		rowCol.add("r");
		
		ArrayList tests = new ArrayList();
		tests.add("s");
		tests.add("m");
		tests.add("l");
		
		ArrayList requirements = new ArrayList();
		requirements.add("s");
		requirements.add("m");
		requirements.add("l");
		
		ArrayList coverageAmount = new ArrayList();
		coverageAmount.add("s");
		coverageAmount.add("m");
		coverageAmount.add("l");
		
		ArrayList metrics = new ArrayList();
		metrics.add("coverage");
		metrics.add("ratio");
		metrics.add("time");
		
		long stop=0;
		long start=0;
		long reduceTime=0;
		long prioritizeTime=0;
		long totalTime=0;
		int looks=3;
		
		int expCounter = 0;
								
		File resultsFile = new File("data/diatoms/reduce/synthetic_test_results/HarroldGuptaSoffaSyntheticTestResults.dat");
		if(resultsFile.exists())
		{
			resultsFile.delete();
		}
	
		resultsFile.createNewFile();
				
		BufferedWriter resultsWriter = new BufferedWriter(new FileWriter(resultsFile, true));

		try 
		{
			resultsWriter.write("config\t"+"alg\t"+ "metric\t"+"looks\t"+"reduceTime\t"+"reduceSet\t"+"priorTime\t"+"priorOrder\n");
		}

		catch (IOException e) 
		{
			System.out.println("IOException on write");
		}				

		Iterator rowColIterator = rowCol.iterator();
		
		while (rowColIterator.hasNext())
		{
			String balance = (String) rowColIterator.next();
			
			Iterator testsIterator = tests.iterator();
				
			while (testsIterator.hasNext())
			{
				String numTests = (String) testsIterator.next();
				
				Iterator requirementsIterator = requirements.iterator();
			
				while (requirementsIterator.hasNext())
				{
					String numRequirements = (String) requirementsIterator.next();
					
					Iterator coverageAmountIterator = coverageAmount.iterator();

					while (coverageAmountIterator.hasNext())
					{
						String coverage = (String) coverageAmountIterator.next();
					
						Iterator metricsIterator = metrics.iterator();
						while (metricsIterator.hasNext())
						{
							String metric = (String) metricsIterator.next();
							
							for(int replicationFactor=1;replicationFactor<=10;replicationFactor++)
							{
								
								String config = (balance+numTests+numRequirements+coverage+replicationFactor);
								
								ReduceUsingHarroldGuptaSoffa hgs = new ReduceUsingHarroldGuptaSoffa(SetCover.constructSetCoverFromBinary(DIRTODATA+"matrix-"+config,DIRTODATA+"time-"+config));
								
								hgs.setLooksAhead(looks);
							
								System.gc();
								start = System.currentTimeMillis();
								hgs.reduceUsingHGS(metric);
								stop = System.currentTimeMillis();
								
								reduceTime = stop-start;
								
								String coveringSet = hgs.getCoveringTestSetString("Q");
								
								hgs.setSetCover(SetCover.constructSetCoverFromBinary(DIRTODATA+"matrix-"+config,DIRTODATA+"time-"+config));
								System.gc();
								start = System.currentTimeMillis();
								hgs.prioritizeUsingHGS(metric);
								stop= System.currentTimeMillis();
								
								prioritizeTime = stop-start;

								expCounter++;
								System.out.println("Harrold Gupta Soffa Experiment: "+expCounter+"/4860 "+config+" "+metric+" "+totalTime+"ms");

								String prioritizeSet = hgs.getPrioritizedSetString("Q");
								
								try 
								{
									resultsWriter.write(config+"\tHGS\t"+metric+"\t"+looks+"\t+"+reduceTime+"\t"+
															  coveringSet+"\t"+prioritizeTime+"\t"+prioritizeSet+"\n");
								}

								catch (IOException e) 
								{
									System.out.println("IOException on write");
		    					}
	    					}
						}
					}
				}
			}
		}
	//main
	resultsWriter.close();	
	}
}

