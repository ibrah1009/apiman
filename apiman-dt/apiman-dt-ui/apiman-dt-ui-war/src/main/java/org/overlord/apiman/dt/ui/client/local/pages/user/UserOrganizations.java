/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.apiman.dt.ui.client.local.pages.user;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.overlord.apiman.dt.api.beans.summary.OrganizationSummaryBean;
import org.overlord.apiman.dt.ui.client.local.services.NavigationHelperService;
import org.overlord.commons.gwt.client.local.widgets.SpanPanel;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

/**
 * UI component to display the user's list of organizations.
 *
 * @author eric.wittmann@redhat.com
 */
@Dependent
public class UserOrganizations extends FlowPanel implements HasValue<List<OrganizationSummaryBean>> {
    
    @Inject
    NavigationHelperService navHelper;
    
    private List<OrganizationSummaryBean> orgs;

    /**
     * Constructor.
     */
    public UserOrganizations() {
        getElement().setClassName("apiman-organizations"); //$NON-NLS-1$
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<OrganizationSummaryBean>> handler) {
        return super.addHandler(handler, ValueChangeEvent.getType());
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public List<OrganizationSummaryBean> getValue() {
        return orgs;
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
     */
    @Override
    public void setValue(List<OrganizationSummaryBean> value) {
        setValue(value, false);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object, boolean)
     */
    @Override
    public void setValue(List<OrganizationSummaryBean> value, boolean fireEvents) {
        orgs = value;
        clear();
        refresh();
    }

    /**
     * Refresh the display with the current value.
     */
    public void refresh() {
        if (orgs != null) {
            for (OrganizationSummaryBean bean : orgs) {
                Widget row = createOrgRow(bean);
                add(row);
            }
        }
    }

    /**
     * Creates a single organization row.
     * @param bean
     */
    private Widget createOrgRow(OrganizationSummaryBean bean) {
        FlowPanel container = new FlowPanel();
        container.getElement().setClassName("container-fluid"); //$NON-NLS-1$
        container.getElement().addClassName("apiman-summaryrow"); //$NON-NLS-1$
        
        FlowPanel row1 = new FlowPanel();
        container.add(row1);
        row1.getElement().setClassName("row"); //$NON-NLS-1$
        SpanPanel title = new SpanPanel();
        row1.add(title);
        title.getElement().setClassName("title"); //$NON-NLS-1$
        Anchor a = new Anchor();
        title.add(a);
        a.setHref("#");
        a.setText(bean.getName());
//        a.setHref(navHelper.createHrefToPage(OrgAppsPage.class, bean.getId()));

        FlowPanel row2 = new FlowPanel();
        container.add(row2);
        row2.getElement().setClassName("row"); //$NON-NLS-1$
        InlineLabel description = new InlineLabel(bean.getDescription());
        row2.add(description);
        description.getElement().setClassName("description"); //$NON-NLS-1$
        
        container.add(new HTMLPanel("<hr/>")); //$NON-NLS-1$
        
        return container;
    }
}