package org.doubleoops.mosh;

import java.lang.reflect.Method;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

class MoshExecutionListener implements TestExecutionListener {

	private final MoshDocument docRoot;
	private String studentTestClass;
	private String studentTestMethod;

	private TestPlan testPlan;

	public MoshExecutionListener(MoshDocument docRoot) {
		this.docRoot = docRoot;
	}

	@Override
	public void testPlanExecutionStarted(TestPlan testPlan) {
		var config = testPlan.getConfigurationParameters();
		studentTestClass = config.get(MoshEngine.STUDENT_TEST_CLASS_CONFIG_KEY).get();
		studentTestMethod = config.get(MoshEngine.STUDENT_TEST_METHOD_CONFIG_KEY).get();

		// shouldn't need this, but hanging on to it for debug
		// purposes elsewhere
		this.testPlan = testPlan;
	}

	@Override
	public void executionStarted(TestIdentifier testIdentifier) {

		/*
		 * At the beginning of test execution for a MetaTest class, create a node in the
		 * document for that class (if needeD), and create a student test node underneath
		 */
		var srcOpt = testIdentifier.getSource();
		if (srcOpt.isEmpty())
			return;
		var src = srcOpt.get();

		if (src instanceof ClassSource) {
			var classSrc = (ClassSource) src;
			Class<?> klass = classSrc.getJavaClass();
			var name = klass.getSimpleName();
			docRoot.createMTNodeIfAbsent(name);
			var mtNode = docRoot.getMTNode(name);
			mtNode.createSTNodeIfAbsent(studentTestClass, studentTestMethod);
		}

	}

	@Override
	public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
		/**
		 * At the end of a meta-test method execution, update the appropriate STNode
		 * with the pass/fail results.
		 */
		var srcOpt = testIdentifier.getSource();
		if (srcOpt.isEmpty())
			return;
		var src = srcOpt.get();

		if (src instanceof MethodSource) {
			var methodSrc = (MethodSource) src;
			Method meth = methodSrc.getJavaMethod();
			Class<?> klass = methodSrc.getJavaClass();

			var metaTestMethodName = meth.getName();
			var metaTestClassName = klass.getSimpleName();

			var mtNode = docRoot.getMTNode(metaTestClassName);
			var stNode = mtNode.getSTNode(studentTestClass, studentTestMethod);
			if (testExecutionResult.getStatus().equals(TestExecutionResult.Status.SUCCESSFUL)) {
				stNode.markAsPassed(metaTestMethodName);
			} else {
				stNode.markAsFailed(metaTestMethodName);
			}

		}
	}

	public void executionSkipped(TestIdentifier testIdentifier, String reason) {
		System.out.println(reason);
	}

	public MoshDocument getDocument() {
		return docRoot;
	}
}
