package org.xwiki.projectmanagement;

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

import java.util.List;

import org.apache.http.NameValuePair;
import org.xwiki.component.annotation.Role;

/**
 * Provides various information in the context of a project management macro execution, such as the source parameters.
 *
 * @version $Id$
 * @since 1.0
 */
@Role
public interface ProjectManagementMacroContext
{
    /**
     * @return a map representing the source parameters passed to the project management macro.
     */
    List<NameValuePair> getSourceParams();
}
