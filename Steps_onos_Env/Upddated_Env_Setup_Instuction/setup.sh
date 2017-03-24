# PREREQUISITE
# onos and onos-yang-tools should be in /home/root1
# keep yangdemo app in /home/root1
# put setup.sh in /home/root1
# build onos and onos-yang-tools
# run setup.sh 
# ok clean and activate the required apps.
#!/usr/bin/env bash
SOURCE=`pwd`
TOOL="$SOURCE/onos-yang-tools"
ONOS="$SOURCE/onos"

# running patch-yang-libs
sh $ONOS/tools/dev/bin/patch-yang-libs
cp -rf $SOURCE/yangdemo /home/root1/onos/apps/

# replacing the YANG.jar
cp $TOOL/compiler/plugin/buck/target/onos-yang-compiler-buck-plugin-1.12-SNAPSHOT.jar $ONOS/bin/plugins/
rm -rf $ONOS/bin/plugins/yang.jar
mv $ONOS/bin/plugins/onos-yang-compiler-buck-plugin-1.12-SNAPSHOT.jar $ONOS/bin/plugins/yang.jar

# adding demo app in module.defs
sed -i 's#'//apps/l3vpn:onos-apps-l3vpn-oar'#'//apps/yangdemo:onos-apps-yangdemo-oar"'","\n    '"//apps/l3vpn:onos-apps-l3vpn-oar'#g' $ONOS/modules.defs


