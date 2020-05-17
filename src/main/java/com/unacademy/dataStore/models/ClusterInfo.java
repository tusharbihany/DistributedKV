package com.unacademy.dataStore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
/*
   Maintains cluster info
 */
public class ClusterInfo {
    private Set<Server> nodes;
}
