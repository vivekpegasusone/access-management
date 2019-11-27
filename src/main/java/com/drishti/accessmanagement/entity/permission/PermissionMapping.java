package com.drishti.accessmanagement.entity.permission;

import com.drishti.accessmanagement.entity.action.Action;
import com.drishti.accessmanagement.entity.resource.Resource;
import com.drishti.accessmanagement.entity.role.Role;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "permission_mappings")
public class PermissionMapping implements Serializable {

  private static final long serialVersionUID = -7084677788576384413L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "roleId", nullable = false)
  private Role role;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "permissionId", nullable = false)
  private Permission permission;

}
