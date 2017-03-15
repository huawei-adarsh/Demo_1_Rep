/*
 * Copyright 2017-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.l3vpn.netl3vpn;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.onosproject.core.CoreService;
import org.onosproject.yang.gen.v1.l3vpn.comm.type.rev20141225.NeL3VpncommType;
import org.onosproject.yang.gen.v1.namespace1.rev20130715.Demo1;
import org.onosproject.yang.gen.v1.ne.bgpcomm.rev20141225.NeBgpcomm;
import org.onosproject.yang.gen.v1.ne.bgpcomm.type.rev20141225.NeBgpcommType;
import org.onosproject.yang.gen.v1.ne.l3vpn.api.rev20141225.NeL3VpnApi;
import org.onosproject.yang.gen.v1.ne.l3vpn.comm.rev20141225.NeL3Vpncomm;
import org.onosproject.yang.model.YangModel;
import org.onosproject.yang.model.YangModuleId;
import org.onosproject.yang.runtime.DefaultAppModuleInfo;
import org.onosproject.yang.runtime.DefaultModelRegistrationParam;
import org.onosproject.yang.runtime.ModelRegistrationParam;
import org.onosproject.yang.runtime.YangModelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

import static org.onosproject.yang.runtime.helperutils.YangApacheUtils.getYangModel;

/**
 * The IETF net l3vpn manager implementation.
 */
@Component(immediate = true)
public class Demo1Manager {

    private static final String APP_ID = "org.onosproject.app.yangdemo";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected CoreService coreService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected YangModelRegistry modelRegistry;

    private ModelRegistrationParam regParam = null;

    @Activate
    protected void activate() {
        coreService.registerApplication(APP_ID);
        registerModel();
        log.info("Started");
        //TODO implementation
    }

    @Deactivate
    protected void deactivate() {
        modelRegistry.unregisterModel(regParam);
        log.info("Stopped");
    }

    private void registerModel() {
        YangModel model = getYangModel(Demo1.class);
        Iterator<YangModuleId> it = model.getYangModulesId().iterator();

        //Create model registration param.
        ModelRegistrationParam.Builder b =
                DefaultModelRegistrationParam.builder().setYangModel(model);

        YangModuleId id;
        while (it.hasNext()) {
            id = it.next();
            switch (id.moduleName()) {
                case "demo1":
                    b.addAppModuleInfo(id, new DefaultAppModuleInfo(
                            Demo1.class, null));
                    break;
                case "ne-bgpcomm":
                    b.addAppModuleInfo(id, new DefaultAppModuleInfo(
                            NeBgpcomm.class, null));
                    break;
                case "ne-bgpcomm-type":
                    b.addAppModuleInfo(id, new DefaultAppModuleInfo(
                            NeBgpcommType.class, null));
                    break;
                case "ne-l3vpn-api":
                    b.addAppModuleInfo(id, new DefaultAppModuleInfo(
                            NeL3VpnApi.class, null));
                    break;
                case "ne-l3vpncomm":
                    b.addAppModuleInfo(id, new DefaultAppModuleInfo(
                            NeL3Vpncomm.class, null));
                    break;
                case "ne-l3vpncomm-type":
                    b.addAppModuleInfo(id, new DefaultAppModuleInfo(
                            NeL3VpncommType.class, null));
                    break;
                default:
                    break;
            }
        }
        regParam = b.build();
        modelRegistry.registerModel(regParam);
    }
}
