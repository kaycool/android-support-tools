# StrongSupport
android 扩展包集合


	repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  
  dependencies {
 	implementation ('com.github.kaycool:StrongSupport:1.0.3'){
        exclude group: 'com.android.support', module: 'support-v4'
        exclude group: 'com.android.support', module: 'design'
        exclude group: 'com.android.support.constraint', module: 'constraint-layout'
    	}
  }

configurations.all {
    resolutionStrategy.eachDependency { DependencyResolveDetails details ->
        def requested = details.requested
        if (requested.group == 'com.android.support') {
            if (!requested.name.startsWith("multidex")) {
                details.useVersion 'your project appcomcat verison'
            }
        }
    }
}
