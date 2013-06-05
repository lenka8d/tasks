package com.process.test_gps_sms;

import java.util.ArrayList;
import java.util.List;
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

import com.example.iamhere.GpsHandler;
import com.example.iamhere.SmsHandler;

import android.app.Activity;
import android.os.Bundle;

public class HelloAndroidActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RuleFlowProcess process = new RuleFlowProcess();
		process.setId("com.example.androidprocessjbpm.hello");
		process.setPackageName("com.example.androidprocessjbpm");

		VariableScope variableScope = (VariableScope) process.getContexts(
				VariableScope.VARIABLE_SCOPE).get(0);

		List<Variable> variables = new ArrayList<Variable>();

		Variable latitude = new Variable();
		latitude.setName("latitude");
		latitude.setType(new StringDataType());
		variables.add(latitude);

		Variable longitude = new Variable();
		longitude.setName("longitude");
		longitude.setType(new StringDataType());
		variables.add(longitude);
		
		variableScope.setVariables(variables);

		StartNode startNode = new StartNode();
		startNode.setId(1);
		process.addNode(startNode);
		WorkItemNode workItemNode1 = new WorkItemNode();
		Work work1 = new WorkImpl();
		work1.setName("GpsLocation");
		workItemNode1.setWork(work1);
		workItemNode1.addOutMapping("latitude", "latitude");
		workItemNode1.addOutMapping("longitude", "longitude");
		workItemNode1.setId(2);
		process.addNode(workItemNode1);
		WorkItemNode workItemNode2 = new WorkItemNode();
		Work work2 = new WorkImpl();
		work2.setName("SendSms");
		workItemNode2.setWork(work2);
		workItemNode2.addInMapping("latitude", "latitude");
		workItemNode2.addInMapping("longitude", "longitude");
		workItemNode2.setId(3);
		process.addNode(workItemNode2);
		EndNode endNode = new EndNode();
		endNode.setId(4);
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

		ksession.getWorkItemManager().registerWorkItemHandler("GpsLocation", new GpsHandler(this));

		ksession.getWorkItemManager().registerWorkItemHandler("SendSms", new SmsHandler());        
         
		ksession.startProcess("com.example.androidprocessjbpm.hello");
	}

}