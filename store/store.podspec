Pod::Spec.new do |spec|
    spec.name                     = 'store'
    spec.version                  = '5.0.0-alpha1'
    spec.homepage                 = 'https://github.com/MobileNativeFoundation/Store'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'Store5'
    spec.vendored_frameworks      = 'build/cocoapods/framework/store.framework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '13'
                
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':store',
        'PRODUCT_MODULE_NAME' => 'store',
    }
                
    spec.script_phases = [
        {
            :name => 'Build store',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$COCOAPODS_SKIP_KOTLIN_BUILD" ]; then
                  echo "Skipping Gradle build task invocation due to COCOAPODS_SKIP_KOTLIN_BUILD environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
                
end