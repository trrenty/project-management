package org.xwiki.projectmanagement.macro;

/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

import org.xwiki.livedata.macro.LiveDataMacroParameters;
import org.xwiki.projectmanagement.ProjectManagementFilter;
import org.xwiki.projectmanagement.ProjectManagementProperties;
import org.xwiki.projectmanagement.internal.WorkItemsDisplayer;
import org.xwiki.properties.annotation.PropertyDisplayType;
import org.xwiki.properties.annotation.PropertyHidden;
import org.xwiki.stability.Unstable;

/**
 * @version $Id$
 * @since 1.0
 */
@Unstable
public class ProjectManagementMacroParameters extends LiveDataMacroParameters
{
    private WorkItemsDisplayer workItemsDisplayer = WorkItemsDisplayer.LIVEDATA;

    /**
     * Default constructor.
     */
    public ProjectManagementMacroParameters()
    {
        // Set default values.
        setProperties("identifier.value,type,summary.value,description,startDate,resolved,assignees");
        setLimit(5);
    }

    @Override
    public String getProperties()
    {
        return super.getProperties();
    }

    @PropertyDisplayType(ProjectManagementProperties.class)
    @Override
    public void setProperties(String properties)
    {
        super.setProperties(properties);
    }

    @PropertyDisplayType(ProjectManagementFilter.class)
    @Override
    public void setFilters(String filters)
    {
        super.setFilters(filters);
    }

    /**
     * @return the displayer id that determines how the work items should be displayed.
     */
    public WorkItemsDisplayer getWorkItemsDisplayer()
    {
        return workItemsDisplayer;
    }

    /**
     * @param workItemsDisplayer see {@link #getWorkItemsDisplayer()}.
     */
    @PropertyDisplayType(WorkItemsDisplayer.class)
    public void setWorkItemsDisplayer(WorkItemsDisplayer workItemsDisplayer)
    {
        this.workItemsDisplayer = workItemsDisplayer;
    }

    // We hide some parameters in order to make the macro simpler to use.

    @PropertyHidden
    @Override
    public void setPageSizes(String pageSizes)
    {
        super.setPageSizes(pageSizes);
    }

    @PropertyHidden
    @Override
    public Boolean getShowPageSizeDropdown()
    {
        return super.getShowPageSizeDropdown();
    }

    @PropertyHidden
    @Override
    public void setLayouts(String layouts)
    {
        super.setLayouts(layouts);
    }

    @PropertyHidden
    @Override
    public void setSource(String source)
    {
        super.setSource(source);
    }

    @PropertyHidden
    @Override
    public void setSourceParameters(String sourceParameters)
    {
        super.setSourceParameters(sourceParameters);
    }

    @PropertyHidden
    @Override
    public void setId(String id)
    {
        super.setId(id);
    }
}
