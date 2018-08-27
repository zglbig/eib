using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class HallRedPacketListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<HallRedPacketDto> redList;
}
