package org.doubleoops.heavymeta.test.codetemplates;

import org.doubleoops.heavymeta.HeavyMeta;
import org.junit.jupiter.api.extension.RegisterExtension;

public class MetaTestCodeTemplatesSandbox1 {
	
	@RegisterExtension
	static HeavyMeta metaTester = new HeavyMeta(Object.class, "testMethodName");
}
