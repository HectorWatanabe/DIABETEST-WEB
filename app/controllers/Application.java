package controllers;

import play.*;
import play.mvc.*;
import views.html.*;
import java.util.*;
import play.data.DynamicForm;
import weka.core.Instances;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
//Librerías para leer el archivo ARFF
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import models.Weka;
import models.Summary;
import models.Test;

public class Application extends Controller {

    public static Result index()
    {
        return ok(index.render());
    }

    public static Result about()
    {
        Weka weka = new Weka();
        Summary summary = new Summary();

        String result = "";

        result = weka.statistics();

        summary = weka.obtenSummary(result);

    	return ok(about.render(summary));
    }

    public static Result test()
    {
        return ok(test.render());
    }

    public static Result evaluation()
    {
        DynamicForm df = play.data.Form.form().bindFromRequest();

        Test datos = new Test();

        datos.numeroembarazos = df.get("numeroembarazos");
        datos.glucosaayuno = df.get("glucosaayuno");
        datos.presion = df.get("presion");
        datos.mediatriceps = df.get("mediatriceps");
        datos.insulina = df.get("insulina");
        datos.indicemasacorporal = df.get("indicemasacorporal");
        datos.funcionpedigree = df.get("funcionpedigree");
        datos.edad = df.get("edad");
        datos.diabetes = df.get("diabetes");

        Weka weka = new Weka();
        weka.createFileTest(datos);

        String resultados = weka.instancesData();

        resultados = resultados.substring( resultados.lastIndexOf(",") + 1);

        return ok(finalTest.render(resultados));
    }

    public static Result store()
    {
        try {

            BufferedWriter out = null;

            Weka weka = new Weka();
            String resultados = weka.instancesData();            

            out = new BufferedWriter(new FileWriter("public/files/diabetes.arff", true));   
            out.write(resultados + "\n");
            out.close(); 
            return ok(index.render());

        } catch (Exception e) {   
            System.out.println(e);
            return ok(index.render());  
        }
    }

}
