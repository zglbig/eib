using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game2PositionPlayerInfoListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<Game2PositionPlayerInfoDto> list;
}
