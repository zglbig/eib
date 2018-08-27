using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class FriendListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<BasePlayerDto> playerDtos;
}
