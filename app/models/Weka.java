package models;

//Librerías para leer el archivo ARFF
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

//Librerías para obtener estadísticas
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;

import java.io.BufferedWriter;
import java.io.FileWriter;

import models.Test;

import java.io.PrintWriter;

public class Weka{

	public void readFile()
	{
		try
		{
			DataSource source = new DataSource("public/files/diabetes.arff");
			Instances data = source.getDataSet();

			System.out.println(data);

			if (data.classIndex() == -1)
			{
			  	data.setClassIndex(data.numAttributes() - 1);
			}

		}
		catch(Exception e)
		{
			System.out.println("Ocurrio un error al leer el archivo");
		}

	}

	public String statistics()
	{
		try
		{
			String[] options = new String[2];
 			options[0] = "-t";
 			options[1] = "public/files/diabetes.arff";
 			String result = Evaluation.evaluateModel(new J48(), options);

 			return result;
		}
		catch(Exception e)
		{
			return "No se analizaron los datos";
		}
	}

	public Summary obtenSummary(String result)
	{
		try
		{
			String correctly_classified_instances = result.substring(
							result.indexOf("Correctly Classified Instances") + 30, 
							result.indexOf("Correctly Classified Instances") + 42);

			String correctly_classified_instances_2 = result.substring(
							result.indexOf("Correctly Classified Instances") + 42, 
							result.indexOf("Correctly Classified Instances") + 64);

			String incorrectly_classified_instances = result.substring(
							result.indexOf("Incorrectly Classified Instances") + 39, 
							result.indexOf("Incorrectly Classified Instances") + 42);

			String incorrectly_classified_instances_2 = result.substring(
							result.indexOf("Incorrectly Classified Instances") + 57, 
							result.indexOf("Incorrectly Classified Instances") + 64);

			String kappa_statistic = result.substring(
							result.indexOf("Kappa statistic") + 15, 
							result.indexOf("Kappa statistic") + 48);

			String mean_absolute_error = result.substring(
							result.indexOf("Mean absolute error") + 41, 
							result.indexOf("Mean absolute error") + 47);

			String root_mean_squared_error = result.substring(
				result.indexOf("Root mean squared error") + 41, 
				result.indexOf("Root mean squared error") + 47);

			String relative_absolute_error = result.substring(
				result.indexOf("Relative absolute error") + 40, 
				result.indexOf("Relative absolute error") + 47);

			String root_relative_squared_error = result.substring(
				result.indexOf("Root relative squared error") + 40, 
				result.indexOf("Root relative squared error") + 47);

			String total_number_of_instances = result.substring(
				result.indexOf("Total Number of Instances") + 39, 
				result.indexOf("Total Number of Instances") + 42);
   

			Summary summary = new Summary();
			summary.kappa_statistic = kappa_statistic;
			summary.correctly_classified_instances = correctly_classified_instances;
			summary.correctly_classified_instances_2 = correctly_classified_instances_2;
			summary.incorrectly_classified_instances = incorrectly_classified_instances;
			summary.incorrectly_classified_instances_2 = incorrectly_classified_instances_2;
			summary.mean_absolute_error = mean_absolute_error;
			summary.root_mean_squared_error = root_mean_squared_error;
			summary.relative_absolute_error = relative_absolute_error;
			summary.root_relative_squared_error = root_relative_squared_error;
			summary.total_number_of_instances = total_number_of_instances;

			return summary;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public void createFileTest(Test datos)
	{
		try{
		    PrintWriter writer = new PrintWriter("public/files/test.arff", "UTF-8");
			writer.println("@relation diabetes");
			writer.println("@attribute 'numeroembarazos' real");
			writer.println("@attribute 'glucosaayuno' real");
			writer.println("@attribute 'presion' real");
			writer.println("@attribute 'mediatriceps' real");
			writer.println("@attribute 'insulina' real");
			writer.println("@attribute 'indicemasacorporal' real");
			writer.println("@attribute 'funcionpedigree' real");
			writer.println("@attribute 'edad' real");
			writer.println("@attribute 'diabetes' { negativo, positivo}");
			writer.println("@data");
			writer.println(
						datos.numeroembarazos + "," + 
		    		  	datos.glucosaayuno + "," + 
		    		  	datos.presion + "," +
		    		  	datos.mediatriceps + "," +
		    		  	datos.insulina + "," +
		    		  	datos.indicemasacorporal + "," +
		    		  	datos.funcionpedigree + "," +
		    		  	datos.edad + "," +
		    		  	"?");

		    writer.close();
		} catch (Exception e) {
		   System.out.println(e);
		}
	}

	public String instancesData()
	{
		try
		{
			DataSource source = new DataSource("public/files/diabetes.arff");
			Instances data = source.getDataSet();

			if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);

			DataSource source2 = new DataSource("public/files/test.arff");
        	Instances test = source2.getDataSet();

        	if (test.classIndex() == -1)
            test.setClassIndex(data.numAttributes() - 1);

        	J48 j48 = new J48();
        	j48.buildClassifier(data);

        	double label = j48.classifyInstance(test.instance(0));
        	test.instance(0).setClassValue(label);

        	return test.instance(0).toString();

		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}

}

