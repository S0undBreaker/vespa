// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.vespa.hosted.controller.api.integration.athens.mock;

import com.yahoo.vespa.hosted.controller.api.integration.athens.ApplicationAction;
import com.yahoo.vespa.hosted.controller.api.identifiers.ApplicationId;
import com.yahoo.vespa.hosted.controller.api.identifiers.AthensDomain;
import com.yahoo.vespa.hosted.controller.api.integration.athens.AthensPrincipal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author bjorncs
 */
public class AthensDbMock {

    public final Map<AthensDomain, Domain> domains = new HashMap<>();

    public AthensDbMock addDomain(Domain domain) {
        domains.put(domain.name, domain);
        return this;
    }

    public static class Domain {

        public final AthensDomain name;
        public final Set<AthensPrincipal> admins = new HashSet<>();
        public final Set<AthensPrincipal> tenantAdmins = new HashSet<>();
        public final Map<ApplicationId, Application> applications = new HashMap<>();
        public boolean isVespaTenant = false;

        public Domain(AthensDomain name) {
            this.name = name;
        }

        public Domain admin(AthensPrincipal user) {
            admins.add(user);
            return this;
        }

        public Domain tenantAdmin(AthensPrincipal user) {
            tenantAdmins.add(user);
            return this;
        }

        /**
         * Simulates establishing Vespa tenancy in Athens.
         */
        public void markAsVespaTenant() {
            isVespaTenant = true;
        }

    }

    public static class Application {

        public final Map<ApplicationAction, Set<AthensPrincipal>> acl = new HashMap<>();

        public Application() {
            acl.put(ApplicationAction.deploy, new HashSet<>());
            acl.put(ApplicationAction.read, new HashSet<>());
            acl.put(ApplicationAction.write, new HashSet<>());
        }

        public Application addRoleMember(ApplicationAction action, AthensPrincipal user) {
            acl.get(action).add(user);
            return this;
        }
    }

}
