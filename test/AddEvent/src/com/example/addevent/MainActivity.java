package com.example.addevent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.definition.KnowledgePackage;
import org.drools.impl.EnvironmentFactory;
import org.drools.process.core.Work;
import org.drools.process.core.datatype.impl.type.StringDataType;
import org.drools.process.core.impl.WorkImpl;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;
import org.jbpm.process.ProcessBaseFactoryService;
import org.jbpm.process.ProcessPackage;
import org.jbpm.process.core.context.variable.Variable;
import org.jbpm.process.core.context.variable.VariableScope;
import org.jbpm.ruleflow.core.RuleFlowProcess;
import org.jbpm.workflow.core.impl.ConnectionImpl;
import org.jbpm.workflow.core.impl.NodeImpl;
import org.jbpm.workflow.core.node.EndNode;
import org.jbpm.workflow.core.node.StartNode;
import org.jbpm.workflow.core.node.WorkItemNode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RuleFlowProcess process = new RuleFlowProcess();
		process.setId("com.example.androidprocessjbpm.hello");
		process.setPackageName("com.example.androidprocessjbpm");

		VariableScope variableScope = (VariableScope) process.getContexts(
				VariableScope.VARIABLE_SCOPE).get(0);

		List<Variable> variables = new ArrayList<Variable>();

		Variable event = new Variable();
		event.setName("event");
		event.setType(new StringDataType());
		variables.add(event);

		Variable description = new Variable();
		description.setName("description");
		description.setType(new StringDataType());
		variables.add(description);
		
		Variable place = new Variable();
		place.setName("place");
		place.setType(new StringDataType());
		variables.add(place);
		
		Variable date = new Variable();
		date.setName("date");
		date.setType(new StringDataType());
		variables.add(date);
		
		Variable time = new Variable();
		time.setName("time");
		time.setType(new StringDataType());
		variables.add(time);
				
		variableScope.setVariables(variables);

		StartNode startNode = new StartNode();
		startNode.setId(1);
		process.addNode(startNode);
		WorkItemNode workItemNode1 = new WorkItemNode();
		Work work1 = new WorkImpl();
		work1.setName("ScheduleEvent");
		workItemNode1.setWork(work1);
		workItemNode1.addOutMapping("event", "event");
		workItemNode1.addOutMapping("description", "description");
		workItemNode1.addOutMapping("place", "place");
		workItemNode1.addOutMapping("date", "date");
		workItemNode1.addOutMapping("time", "time");
		workItemNode1.setId(2);
		process.addNode(workItemNode1);
		WorkItemNode workItemNode2 = new WorkItemNode();
		Work work2 = new WorkImpl();
		work2.setName("AddEvent");
		workItemNode2.setWork(work2);
		workItemNode2.addInMapping("event", "event");
		workItemNode2.addInMapping("description", "description");
		workItemNode2.addInMapping("place", "place");
		workItemNode2.addInMapping("date", "date");
		workItemNode2.addInMapping("time", "time");
		workItemNode2.setId(3);
		process.addNode(workItemNode2);
		EndNode endNode = new EndNode();
		endNode.setId(4);
		endNode.isTerminate();
		process.addNode(endNode);

		new ConnectionImpl(startNode, NodeImpl.CONNECTION_DEFAULT_TYPE,
				workItemNode1, NodeImpl.CONNECTION_DEFAULT_TYPE);
		new ConnectionImpl(workItemNode1, NodeImpl.CONNECTION_DEFAULT_TYPE,
				workItemNode2, NodeImpl.CONNECTION_DEFAULT_TYPE);
		new ConnectionImpl(workItemNode2, NodeImpl.CONNECTION_DEFAULT_TYPE,
				endNode, NodeImpl.CONNECTION_DEFAULT_TYPE);

		KnowledgeBaseFactory
				.setKnowledgeBaseServiceFactory(new ProcessBaseFactoryService());
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		List<KnowledgePackage> packages = new ArrayList<KnowledgePackage>();
		ProcessPackage p = new ProcessPackage(
				"com.example.androidprocessjbpm.hello");
		p.addProcess(process);
		packages.add(p);
		kbase.addKnowledgePackages(packages);

		Properties properties = new Properties();
		properties.put("drools.processInstanceManagerFactory",
						"org.jbpm.process.instance.impl.DefaultProcessInstanceManagerFactory");
		properties.put("drools.processSignalManagerFactory",
				"org.jbpm.process.instance.event.DefaultSignalManagerFactory");
		KnowledgeSessionConfiguration config = KnowledgeBaseFactory
				.newKnowledgeSessionConfiguration(properties);
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession(
				config, EnvironmentFactory.newEnvironment());
		
		ksession.getWorkItemManager().registerWorkItemHandler("ScheduleEvent",
				new WorkItemHandler() {
					public void abortWorkItem(WorkItem workItem,
							WorkItemManager m) {
					}
					public void executeWorkItem(final WorkItem i, final WorkItemManager m) {
				 
						setContentView(R.layout.schedule);
						Button button = (Button) findViewById(R.id.button1);
																				
						button.setOnClickListener(new Button.OnClickListener() {
							public void onClick(View v) {
								 EditText text = (EditText) findViewById(R.id.editText1);
								 String event = text.getText().toString();
								 EditText text1 = (EditText) findViewById(R.id.editText2);
								 String description = text1.getText().toString();
								 EditText text2 = (EditText) findViewById(R.id.editText5);
								 String place = text2.getText().toString();
								 EditText text3 = (EditText) findViewById(R.id.editText4);
								 String time = text3.getText().toString();
								 EditText text4 = (EditText) findViewById(R.id.editText3);
								 String date = text4.getText().toString();
								 
								 Map<String, Object> results = new HashMap<String, Object>();
								 
								 results.put("event", event);
								 results.put("description", description);
								 results.put("place", place);
								 results.put("date", date);
								 results.put("time", time);
								 
								 m.completeWorkItem(i.getId(), results);
								 
							}
						});			
					}
		});


		ksession.getWorkItemManager().registerWorkItemHandler("AddEvent", new AddEventHandler(this));       
         
		ksession.startProcess("com.example.androidprocessjbpm.hello");
	}
}		
