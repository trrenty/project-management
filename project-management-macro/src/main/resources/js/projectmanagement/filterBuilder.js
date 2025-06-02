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
define('project-management-filter-builder', ['jquery', 'xwiki-selectize'], function ($) {
  let constraintBuilder = $('.projManagementConstraintBuilder');
  let addButton = $('.projManagementConstraintBuilder #addConstraint');
  let template = $('#projManagConstraintTemplate');
  let addPoint = $('.projManagConstraints');
  let cfg = constraintBuilder.data('cfg');
  let defaultDisplayer = function (type, inputElem, operator, params) {
    let parent = inputElem.parent();
    parent.children().remove();
    parent.append(inputElem);
    inputElem.removeClass('hidden');
    switch (type) {
      case "number":
        inputElem.attr('type', 'number');
        break;
      case "date":
        inputElem.attr('type', 'date');
        break;
      case "list":
        inputElem.addClass('hidden');
        inputElem.attr('type', 'text');
        let listItem = $('<input />').addClass('listItem');
        let listItemType = (params && params.type) || 'text';
        listItem.attr('type',  listItemType)
        parent.append(listItem);
        listItem.on('change', function () {
          let valElemValue = parent.find('.listItem').map((i, e) => $(e).val()).filter(v => v !== '').get().join('|');
          inputElem.val(valElemValue);
          inputElem.trigger('change');
        });
        let addItemButton = $('<div></div>').append($('<span></span>').addClass('fa').addClass('fa-plus'));
        parent.append(addItemButton);
        if (inputElem.val() != '') {
          let vals = inputElem.val().split('|');
          for (let i = 0; i < vals.length; i++) {
            if (i == 0) {
              listItem.val(vals[i]);
            } else {
              listItem.clone(true).val(vals[i]).insertBefore(addItemButton);
            }
          }
        }
        addItemButton.on('click', function() {
          listItem.clone(true).val('').insertBefore(addItemButton);
        });
        break;
      case "boolean":
        //inputElem.xwikiSelectize();
        inputElem.attr('type', 'text');
        break;
      default: // text
        inputElem.attr('type', 'text');

        break;
    }
  };
  let getJson = function () {
    let resultJson = {};
    constraintBuilder.find('.projManagConstraint').each(function (index, constraint) {
      let root = $(constraint);
      let key = $(root.find('select.projManagConstraintName'));
      let operator = $(root.find('select.projManagConstraintOperator'))
      let val = $(root.find('input.projManagConstraintValue'));
      if (!val.val()) {
        return;
      }
      let filter = resultJson[key.val()] || { property: key.val(), constraints: []};
      filter.constraints.push({operator: operator.val(), value: val.val()});
      resultJson[key.val()] = filter;
    });
    return resultJson;
  };
  let addFilter = function (filter) {
    // Clone template and add it to dom.
    let newConstraint = template.clone();
    newConstraint.removeAttr('id');
    newConstraint.removeClass('hidden');
    addPoint.append(newConstraint);
    //newConstraint.find('select').xwikiSelectize({});
    // Handle the operator field. It should change the value field depending on the type.
    newConstraint.find('select.projManagConstraintOperator').on('change', function (e, initFilter) {
      let operatorElem = $(e.target);
      let parent = operatorElem.closest('.projManagConstraint');
      let nameElem = parent.find('.projManagConstraintName');
      let valElem = parent.find('.projManagConstraintValue');
      let property = cfg.find(i => i.id == nameElem.val());
      if (initFilter) {
        operatorElem.val(initFilter.constraints[0].operator)
      }
      // If the operator does not have a value defined, it means it doesnt need a value.
      if (property.emptyOperators && property.emptyOperators.filter(o => o.id == operatorElem.val())) {
        let parent = valElem.parent();
        parent.children().remove();
        parent.append(valElem);
        valElem.hide();
        return;
      } else {
        valElem.show();
      }

      let valueType = property.filter.id || "text";
      let displayer = property.valueDisplayer || defaultDisplayer;
      let displayerParams = property.filter || {};
      valElem.val('');
      if (initFilter) {
        valElem.val(initFilter.constraints[0].value);
      }
      displayer(valueType, valElem, operatorElem.val(), displayerParams);
      valElem.on('change', function (e) {
        constraintBuilder.trigger('constraintsUpdated', [getJson()]);
      });
    });
    // Handle property element change. It should fill the operator element.
    newConstraint.find('select.projManagConstraintName').on('change', function (e, initFilter) {
      let keyElem = $(e.target);
      let parent = keyElem.closest('.projManagConstraint');
      let valElem = parent.find('.projManagConstraintValue');
      let operatorElem = parent.find('.projManagConstraintOperator');
      valElem.val('');
      if (initFilter) {
      keyElem.val(initFilter.property)}
      if (!keyElem.val()) {
        return;
      }
      let property = cfg.find(i => i.id == keyElem.val());
      if (!property) {
        return;
      }
      operatorElem.find('option').remove();
      property.filter.operators.forEach((operator) => {
        let option = $('<option></option>').attr('value', operator.id).text(operator.name);
        operatorElem.append(option);
      });
      operatorElem.trigger('change', filter);
    }).trigger('change', [filter]);

    if (filter) {
      newConstraint.find('select.projManagConstraintName').val(filter.property).trigger('change');
      newConstraint.find('.projManagConstraintOperator').val(filter.constraints[0].operator);
    }
  };
  let addDisplayer = function(property, displayer) {
    let index = cfg.findIndex(i => i.id == property);
    if (index < 0) {
      console.error(`Property [${property}] is not part of the builder configuration. Could not set displayer.`);
      return;
    }
    cfg[index].valueDisplayer = displayer;
  };
  addButton.on('click', function (event) {
    event.preventDefault();
    addFilter();
  });
  let builder = {
    getConstraints: getJson,
    element: constraintBuilder,
    addFilter: addFilter,
    addDisplayer: addDisplayer,
    cfg: cfg
  };
  window.FilterBuilder = builder;
  return builder;
});