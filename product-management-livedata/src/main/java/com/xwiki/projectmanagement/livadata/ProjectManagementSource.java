package com.xwiki.projectmanagement.livadata;

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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.livedata.LiveDataEntryStore;
import org.xwiki.livedata.LiveDataPropertyDescriptorStore;
import org.xwiki.livedata.LiveDataSource;

/**
 * Something.
 *
 * @version $Id$
 */
@Component
@Named("projectmanagement")
@Singleton
public class ProjectManagementSource implements LiveDataSource
{
    @Inject
    @Named("projectmanagement")
    private LiveDataEntryStore entryStore;

    @Inject
    @Named("projectmanagement")
    private LiveDataPropertyDescriptorStore propertyDescriptorStore;

    @Override
    public LiveDataEntryStore getEntries()
    {
        return entryStore;
    }

    @Override
    public LiveDataPropertyDescriptorStore getProperties()
    {
        return propertyDescriptorStore;
    }
}
