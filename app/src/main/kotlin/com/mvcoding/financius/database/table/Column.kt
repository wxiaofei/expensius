/*
 * Copyright (C) 2015 Mantas Varnagiris.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.mvcoding.financius.database.table

internal fun column(table: Table, name: String, type: Column.Type, defaultValue: String = ""): Column {
    return Column("${table.name}_$name", type, defaultValue)
}

data class Column(
        val name: String,
        private val type: Column.Type,
        private val defaultValue: String = "") {

    fun createScript() = "$name $type ${if (defaultValue.isBlank()) "" else "default $defaultValue"}"

    enum class Type(val dataType: String) {
        Text("text"),
        TextPrimaryKey("text primary key"),
        Integer("integer"),
        Real("real"),
        Boolean("boolean"),
        DateTime("datetime");

        override fun toString(): String {
            return dataType
        }
    }
}